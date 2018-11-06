#!/usr/bin/env perl

use strict;
use warnings;
use Cwd;
use Term::ANSIColor;
use Data::Dumper;
use Octo::Common qw (  ReadInputFile CreateCSVFile SetLogs ReadMetadata);


#-- check for passed argument
if (@ARGV != 2) {
  SetLogs("error","please pass MetadataFile DataFile");
  die   "usage : perl process.pl MetadataFile DataFile \n";
}

my ($metadata_file,$data) = @ARGV;

#-- get extention of file with . to check weather it is .csv or not
my ($ext) = $metadata_file =~ /(\.[^.]+)$/;

#-- print file name
print colored("\nInput FileName : \t" .$metadata_file . "\n\n","yellow");
SetLogs("info","Filname : $metadata_file");

#-- only proceed if file is "txt" file
if ($ext eq ".txt") {

  SetLogs("info","Initialize ReadInputFile");

  #-- convert metadata into readable format
  my @metadata = ReadMetadata($metadata_file);

  #-- create headers so we can pass it to generate csv files
  my @headers;
  foreach  (@metadata) {
    push (@headers, $_->[0]);
  }

  #-- read input file and store in an array
  my @daily_summary = ReadInputFile($data, @metadata);

  SetLogs("info","We have processed the file and stored data in daily_summary array");

  print colored("Number of records : \t". scalar @daily_summary . "\n\n", "yellow");

  SetLogs("info", "total records processed " . scalar @daily_summary );

  # -- Create Daily summary Report
  CreateCSVFile(\@daily_summary, \@headers);

  #-- get current working directory for message
  my $cwd = fastgetcwd;
  print colored("Output.csv file created in $cwd \n\n","green");

} else {

  SetLogs("error","Wrong file extention");

  #-- show error MESSAGE
  die colored("Please use correct file extention \n\n",'red');
}
