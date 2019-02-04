#! /usr/bin/perl
#
# The length of the columns is assumed to be in graphemes not bytes
# UTF-8 handling less than optimal but AFAIK defect free
#
use strict;
use feature 'unicode_strings';

my $TERM = "\r\n";
my $recordnumber = 0;
my $columnnumber = 0;

sub graphemes {
	my $string = shift;
	return ( () = $string =~ m/\X/g );
}
sub gsubstr {
	my $string = shift;
	my $length = shift;
	
	my $deliverystring = "";

	while ($length > 0) {
		my $lplus = $length + 1;
		my @lookahead = $string =~ /\G(.{1,$lplus})(?![\x80-\xBF])/sg;
		my @utf8_chunks = $string =~ /\G(.{1,$length})(?![\x80-\xBF])/sg;
		#greedy grapheme search
		while (graphemes(@utf8_chunks[0]) == graphemes(@lookahead[0])
		&& $lplus - $length < 4) {
			@utf8_chunks[0] = @lookahead;
			$lplus++;
			@lookahead = $string =~ /\G(.{1,$lplus})(?![\x80-\xBF])/sg;
		}
		$deliverystring .= @utf8_chunks[0];
		$string =~ s/.{1,$length}//;
		$length -= graphemes(@utf8_chunks[0]);
	}

	return $deliverystring;
}

sub printfield {
	my $field = shift;
	my $type = shift;
	if ($type eq 'date') {
		#1970-01-01
		#01/01/1970
		die "Date field invalid, record $recordnumber, column $columnnumber\n"
			unless $field =~ /^[0-9]{4}\-[0-9]{2}\-[0-9]{2}$/;
		return substr($field, 8, 2) . "/" 
			. substr($field, 5, 2) . "/" 
			. substr($field, 0, 4)
	}
	if ($type eq 'numeric') {
		#numeric (decimal separator '.' ; no thousands separator ; can be negative)
		#demonstrably can have leading spaces
		#regexp can be tweaked if trailing spaces are okay /^ *\-?[0-9]+(\.[0-9]+)? *$/
		die "Field invalid, record $recordnumber, column $columnnumber, '$field'\n"
			unless $field =~ /^ *\-?[0-9]+(\.[0-9]+)?$/;
		$field = 0 + ($field);	# standardise numeric format (optional)
		return $field;
	}
	# $type eq 'string'
	$field =~ s/[ 	]*$//;	# remove trailing spaces and tabs

	# It's unspecified what to do if the string contains double-quote " characters
	# * This probably doesn't matter if they are interior (depends on downstream programs)
	# * A double-quote in the 1st or last position is problematic if unmatched
	# * A double-quote anywhere combined with one or more commas is also an issue
	# The safest/simplest course of action is to replace " with ' where ever they occur
	$field =~ s/"/'/g;

	if ($field =~ /,/) { $field = "\"$field\"" }
	return $field;
}

# Parameter 1: Metafile
# Parameter 2: Datafile
# Parameter 3: Outputfile
die "Usage: converter.pl metafile datafile outputfile\n" if ( $#ARGV < 2 );
my $metafile = shift;
my $datafile = shift;
my $outputfile = shift;

my @metaArray;
my $recordlength = 0;
# Assuming metafile is ASCII
open (META, $metafile) || die "Cannot find / open $metafile\n";
while (<META>) {
	chomp;
	my @cols = split(",");
	die "Metafile lines must have exactly 3 comma-separated fields\n" if ($#cols != 2);
	die "Metafile column 1 must be non-blank\n" if ($cols[0] =~ /^[ 	]*$/);
	die "Metafile column 2 must be numeric\n" if ($cols[1] !~ /^[0-9]+$/);
	die "Metafile column 2 must be non-zero\n" if ($cols[1] == 0);
	die "Metafile column 3 must be 'date'/'string'/'numeric'\n"
		unless ($cols[2] eq 'date' || $cols[2] eq 'string' || $cols[2] eq 'numeric');
	push @metaArray, \@cols;
	$recordlength += $cols[1];
}
close (META);
if ($#metaArray < 0) { die "Metafile $metafile is empty\n" }

open (OUTPUT, "> :encoding(UTF-8)", $outputfile) || die "Cannot open $outputfile for output\n";
my @headerArray;
foreach my $columnArray (@metaArray) { push @headerArray, $columnArray->[0] }
print OUTPUT join(",", @headerArray) . $TERM;

open (DATA, "< :encoding(UTF-8)", $datafile) || die "Cannot find / open $datafile\n";
while (<DATA>) {
	chomp;
	my $input = $_;
	$recordnumber++;
	my @outputArray;
	$columnnumber = 0;

	die "Record $recordnumber incorrect length " . length($input). " not $recordlength\n"
		unless (graphemes($input) == $recordlength);
	foreach my $columnArray (@metaArray) {
		
		my $column = gsubstr $input, $columnArray->[1];
		substr $input, 0, length($column), "";

		$columnnumber++;
		push @outputArray, printfield($column, $columnArray->[2]);
	}
	print OUTPUT join(",", @outputArray) . $TERM;
}
close (DATA);

close (OUTPUT);








exit 1;
