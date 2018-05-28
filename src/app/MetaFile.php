<?php
/**
 * Created by PhpStorm.
 * User: mavperi
 * Date: 28/05/2018
 * Time: 15:29
 */

namespace App;

use Illuminate\Support\Facades\Storage;
use App\OutputLog;

class MetaFile
{

    /**
     *
     * the column definition
     *
     * @var array
     */
    public $colsDefinition;

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


    public function __construct($dataFile, $metaFile)
    {
        $this->colsDefinition = [];
        $this->error = false;
        $this->metaFile = $metaFile;
        $this->dataFile = $dataFile;
        $this->getDefinition();
    }


    /**
     * get the file definition
     */
    private function getDefinition()
    {
        foreach (explode("\n", $contents = Storage::get($this->metaFile)) as $key => $value) {
            $tempRow = explode(",", $value);
            if (count($tempRow) === 3) {
                $this->colsDefinition[$tempRow[0]] = [
                    'name' => $tempRow[0],
                    'size' => $tempRow[1],
                    'format' => $this->checkColumnType($tempRow[2])
                ];
            } else { // incorrect number of fields found
                new OutputLog($this->dataFile, $this->metaFile, 'meta file must contain 3 fields per row', $value, 'error');
                $this->error = true;
            }
        }
    }

    /**
     *
     * check if the definition is allowed
     *
     * @param $type
     * @return string
     */
    private function checkColumnType($type)
    {
        $permitted = ['date', 'string', 'numeric'];
        if (!in_array($type, $permitted)) {
            $this->error = true;
            new OutputLog($this->dataFile, $this->metaFile, 'column type not allowed' . $this->metaFile, $type, 'error');
        }
        return $type;
    }

}