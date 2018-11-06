# you need to have following perl modules installed :

    use "cpanm to install following cpan modules"
    1.  Data::Dumper;
    2.  Text::CSV;
    3.  Term::ANSIColor;
    4.  Log::Log4perl ();
    5.  Log::Log4perl::Level ();
    6.  Cwd

# Assumption :

  - it is closed system and input file is restricted
  - perl 5.16.0 is installed

# How To run :
  - perl perl process.pl MetadataFile DataFile
  - for any issue it will show error message however please check my_errors.log for full information
  - log.conf is set to log everything
  - For Test :  perl test.t MetadataFile DataFile

########### NOTE ###########

If you don't have perl installed and find it difficult to install please let me know asap so I can provide Docker image as well.
I simply did not include it because it will be overkill for such a small task.
