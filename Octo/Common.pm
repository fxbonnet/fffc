#!/usr/bin/env perl
package Octo::Common;

use warnings;
use strict;
use utf8;
use Exporter qw(import);
use Data::Dumper;
use Text::CSV;
use Term::ANSIColor;
use Log::Log4perl ();
use Log::Log4perl::Level ();


# Export the functions on request, so that someone attempting to track down where
# that function comes from can find it easily.
our @EXPORT_OK = qw(  ReadInputFile CreateCSVFile SetLogs ReadMetadata);
our %EXPORT_TAGS = ( all => [@EXPORT_OK] );

#-- initialise log settings
Log::Log4perl->init("log.conf");

#---------------------------------------------------------------------------------------------------
# Title    :  SetLogs
# Syntax   :  SetLogs($log_status , $log_message);
# Function :  append data to log file
# Args     :  $log_status , $log_message
# Returns  :
#---------------------------------------------------------------------------------------------------
sub SetLogs {
  my ( $log_status , $log_message) = @_;

  lc($log_status);

  my $logger = Log::Log4perl->get_logger();

  if ($log_status eq "error") {
    $logger->error($log_message);
  } elsif ( $log_status eq "fatal") {
    $logger->fatal($log_message);
  } elsif ( $log_status eq "warn") {
    $logger->warn($log_message);
  } elsif ( $log_status eq "info") {
    $logger->info($log_message);
  } elsif ( $log_status eq "debug") {
    $logger->debug($log_message);
  } elsif ( $log_status eq "trace") {
    $logger->trace($log_message);
  }
}

#---------------------------------------------------------------------------------------------------
# Title    :  ReadMetadata
# Syntax   :  ReadMetadata($file)
# Function :  ready each line unpack them and push it to array
# Args     :  $file
# Returns  :  return @metadata;
#---------------------------------------------------------------------------------------------------
sub ReadMetadata {
  my ($file) = @_;
  # my %metadata;
  my @metadata;

  my $line=0;

  #-- open file and read it
  open (my $data, '<', $file) or  die "could not open $file \n";

  SetLogs("info","lets start processing");

  while ( my $string = <$data>) {

    $line++;

    #-- clean up string
    chomp $string;
    my @arr = split(/\,/,$string); #split at comma, every line
    push (@metadata, \@arr );
  }

  SetLogs("info","processing finished and data is stored in array");

  #-- return an array
  return @metadata;
}

#---------------------------------------------------------------------------------------------------
# Title    :  ReadInputFile
# Syntax   :  ReadInputFile($file)
# Function :  ready each line unpack them and push it to array
# Args     :  $file
# Returns  :  return @daily_summary;
#---------------------------------------------------------------------------------------------------
sub ReadInputFile {
  my ($file, @metadata) = @_;
  my @daily_summary;

  my $line = 0;
  #-- open file and read it
  open (my $data, '<', $file) or  die "could not open $file \n";

  #-- lets get position ( length ) of each field
  my @unpack_position;
  foreach (@metadata) {
    push (@unpack_position, "A".$_->[1]);
  }

  SetLogs("info","lets create unpack string");
  my $unpack_string = join(" ", @unpack_position);

  SetLogs("info","lets start processing");
  while ( my $string = <$data>) {

    $line++;

    #-- clean up string
    chomp $string;

    #-- we could have assign this to just an array called @test however I decided to assign them to variable for my better understanding
    my ($birth_date, $first_name, $last_name, $weight) = unpack ("$unpack_string", $string);

    $first_name =~ s/^\s+|\s+$//g;
    $last_name =~ s/^\s+|\s+$//g;
    $weight =~ s/^\s+|\s+$//g;
    #-- push necessary information to an array
    push ( @daily_summary,   [ $birth_date, $first_name, $last_name, $weight ]  );
  }

  SetLogs("info","processing finished and data is stored in array");

  # print Dumper \@daily_summary;

  #-- return an array
  return @daily_summary;
}


#---------------------------------------------------------------------------------------------------
# Title    :  CreateCSVFile
# Syntax   :  CreateCSVFile(@daily_summary)
# Function :  will create output.csv file
# Args     :
# Returns  : return 1/0
#---------------------------------------------------------------------------------------------------
sub CreateCSVFile {
  my ($data_ref, $header_ref) = @_;

  my @daily_summary = @{ $data_ref };  # dereferencing and copying each array

  if (scalar @daily_summary > 0) {
    SetLogs("info","atleast there is one row to create output file");
  } else {
    SetLogs("warn","Empty array hence nothing to process");
  }

  my $output_file = "Output.csv";

  my $csv = Text::CSV->new ({
              binary    => 1,   # Allow special character. Always set this
              auto_diag => 1,   # Report irregularities immediately
              sep_char => ',',  # Separator
              eol => $/ ,
            });

  #-- configure headings for output.csv file
  my @heading = @{ $header_ref };

  #-- open file to append data
  open (my $fh,  ">:encoding(utf8)", $output_file) or die " $output_file : $!";

  #-- add heading to csv
  $csv->print($fh, \@heading);

  #-- add each row from array to csv
  $csv->print ($fh, $_) for @daily_summary;

  #-- close file handle
  close $fh or die "$output_file: $!";

  # print file name
  print "Output FileName : \t" .$output_file . "\n\n";
}

1;
