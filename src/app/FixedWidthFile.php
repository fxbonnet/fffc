<?php
/**
 * Created by PhpStorm.
 * User: mavperi
 * Date: 28/05/2018
 * Time: 13:31
 */

namespace App;

use Illuminate\Support\Facades\Storage;
use App\OutputLog;
use App\MetaFile;
use App\RowValidator;

class FixedWidthFile
{

    /**
     *
     * the file containing the meta
     *
     * @var string
     */
    protected $metaFile;

    /**
     *
     * the file containing the data
     *
     * @var string
     */
    protected $dataFile;

    /**
     *
     * whether or not we had errors
     *
     * @var bool
     */
    public $error;

    /**
     *
     * abort on any error
     *
     * @var bool
     */
    public $abortonerror;



    public function __construct($dataFile, $metaFile, $abortOneError)
    {
        $this->error = false;
        $this->metaFile = $metaFile;
        $this->dataFile = $dataFile;
        $this->abortonerror = $abortOneError;
        $this->mf = new MetaFile($dataFile, $metaFile);
        if($this->mf->error === true){
            $this->error = true;
        }
        $this->getAbortOnError(); // check for meta errors
        // process
        $this->processFile();
    }

    private function getAbortOnError(){
        if($this->abortonerror === true && $this->error=== true){
            Storage::delete('/app/output/' . str_replace('/', '_', $this->dataFile .'.csv'));
            echo "\n aborting due to error in processing file, check log. You can run without the --abortonerror option to ignore errors";
            new OutputLog($this->dataFile, $this->metaFile, 'Aborting due to error in processing, you can run without the --abortonerror option to ignore errors', '', 'error');
        }
    }

    /**
     * read file line by line to accommodate large files
     */
    private function processFile()
    {
        // open the existing data file
        $handle = fopen('/var/www/html/storage/' . $this->dataFile, "r") or die("Couldn't get handle");

        // remove the previous converted file if it exists
        Storage::delete('/app/output/' . str_replace('/', '_', $this->dataFile .'.csv'));

        if ($handle) {
            while (!feof($handle)) {
                $buffer = fgets($handle, 4096);
                $rv = new RowValidator($buffer, $this->dataFile, $this->metaFile);
                if($rv->error === true){
                    $this->error = true;
                    $this->getAbortOnError();
                }else{
                    $this->setNewRow($rv->csvLine);
                }

            }
            fclose($handle);
        }
    }


    /**
     *
     * save the new raw to the file
     *
     * @param $row
     */
    private function setNewRow($row)
    {
        $filename = '/app/output/' . str_replace('/', '_', $this->dataFile .'.csv');
        Storage::append($filename, $row);
    }


}