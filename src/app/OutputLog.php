<?php
/**
 * Created by PhpStorm.
 * User: mavperi
 * Date: 28/05/2018
 * Time: 13:45
 */

namespace App;

use Illuminate\Http\File;
use Illuminate\Support\Facades\Storage;

class OutputLog
{

    public function __construct($dataFile, $metaFile, $message, $payload, $level)
    {
        $filename = '/app/output/'.str_replace('/', '_', $dataFile.'-'.$metaFile.'.log');
        Storage::append($filename, json_encode($this->getLogEntry($dataFile, $metaFile, $message, $payload, $level)));
    }


    /**
     *
     * our entry describing the log
     *
     * @param $dataFile
     * @param $metaFile
     * @param $message
     * @param $payload
     * @return array
     */
    private function getLogEntry($dataFile, $metaFile, $message, $payload, $level){
        return [
            'time' => date('Y-m-d H:i:s'),
            'level' => $level,
            'dataFile' => $dataFile,
            'metaFile' => $metaFile,
            'message' => $message,
            'payload' => $payload
        ];
    }


}