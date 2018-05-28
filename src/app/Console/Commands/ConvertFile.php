<?php

namespace App\Console\Commands;

use Illuminate\Console\Command;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Storage;
use App\FixedWidthFile;
use App\OutputLog;

class ConvertFile extends Command
{


    /**
     * The name and signature of the console command.
     *
     * @var string
     */
    protected $signature = 'fffc:process:file {--abortonerror}';

    /**
     * The console command description.
     *
     * @var string
     */
    protected $description = 'Convert a fixed width file to CSV';


    /**
     *
     * the path where files need to be placed
     *
     * @var string
     */
    protected $path;

    /**
     *
     * the output path
     *
     * @var string
     */
    protected $pathOutput;

    /**
     *
     * the array of files
     *
     * @var array
     */
    protected $fileArr;


    /**
     * Create a new command instance.
     *
     * @return void
     */
    public function __construct()
    {
        parent::__construct();
        $this->path = '/app/input/';
        $this->pathOutput = '/app/output/';
        $this->fileArr = Storage::allFiles($this->path);
    }

    /**
     * Execute the console command.
     *
     * @return mixed
     */
    public function handle()
    {
        $this->getWelcomeMessage();
        $this->process();

    }

    /**
     * process the files
     */
    private function process(){
        if(count($this->fileArr) > 1){ // need a file and a metadata to proceed
            $dataFile = $this->choice('Select data file to process?', $this->fileArr);
            $metaFile = $this->choice('Select the metadata file', $this->removeSelected($dataFile, $this->fileArr));
            $fff = new FixedWidthFile($dataFile, $metaFile, $this->option('abortonerror'));
            $this->setOutput($fff, $dataFile, $metaFile);
        }else{ // if we have no files to process we can't go further
            $this->error('It seems like no files are present in ' . $this->path);
            new OutputLog('', '', 'not enough files in '. $this->path, '', 'error');
        }
    }


    /**
     *
     * set the final output
     *
     * @param $fff
     * @param $dataFile
     * @param $metaFile
     */
    private function setOutput($fff, $dataFile, $metaFile){
        if($fff->error == true){
            new OutputLog($dataFile, $metaFile, 'complete with errors check log', '', 'error');
        }else{
            new OutputLog($dataFile, $metaFile, 'complete', '', 'info');
        }
    }

    /**
     * welcome message to the app
     */
    private function getWelcomeMessage(){
        $this->info('');
        $this->info('**************************************************************');
        $this->info(' Convert a file from fixed file format to CSV ');
        $this->info(' Files must be placed in storage/'.$this->path);
        $this->info(' Output files will be placed in storage/'.$this->pathOutput);
        $this->info('**************************************************************');
    }


    /**
     *
     * remove a selected file  from array options to avoid selecting same file as the data and metadata
     *
     * @param string $file
     * @param array $fileArr
     * @return array
     */
    private function removeSelected($file, $fileArr){
        if (($key = array_search($file, $fileArr)) !== false) {
            unset($fileArr[$key]);
        }
        return $fileArr;
    }
}
