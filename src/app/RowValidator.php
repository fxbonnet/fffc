<?php
/**
 * Created by PhpStorm.
 * User: mavperi
 * Date: 28/05/2018
 * Time: 17:01
 */

namespace App;


class RowValidator
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
     * array with all values and the validation checks
     *
     * @var array
     */
    public $rawArrProcessed;

    /**
     *
     * a csv line
     *
     * @var string
     */
    public $csvLine;


    public function __construct($rowStr, $dataFile, $metaFile)
    {
        $this->error = false;
        $this->metaFile = $metaFile;
        $this->dataFile = $dataFile;
        $this->mf = new MetaFile($dataFile, $metaFile);
        $this->rawArrProcessed = $this->getRow($rowStr);
        $this->checkAllErrors();
        $this->csvLine = $this->getCSVLine();
    }

    public function getCSVLine(){
        $na = [];
        foreach($this->rawArrProcessed['content'] as $key=>$value){
            $na[] = $value;
        }
        return implode(',', $na);
    }

    /**
     * fail the row if any of the columns do not pass validation check
     */
    private function checkAllErrors(){
        $result = false;
        foreach($this->rawArrProcessed['validationPass'] as $key=>$value){
            if($value === false){
                $this->error = true;
                new OutputLog($this->dataFile, $this->metaFile, 'validation failed for field', [$key => $value], 'error');
            }
        }
    }

    /**
     *
     * get row array from the data file
     *
     * @param $rowStr
     * @return array
     */
    private function getRow($rowStr)
    {
        try {
            $row = [];
            $previousStart = 0;
            foreach ($this->mf->colsDefinition as $key => $definition) {
                $row[$definition['name']] = $this->removeWhiteSpace(substr($rowStr, $previousStart, $definition['size']));
                $previousStart = $previousStart + $definition['size'];
            }
            return $this->checkRow($row);
        } catch (\Exception $e) {
            $this->error = true;
            echo "\n error! ". $e->getMessage();
            new OutputLog($this->dataFile, $this->metaFile, 'error processing row', $rowStr, 'error');
        }
    }

    /**
     *
     * run the releveant validation
     *
     * @param $row
     * @return array
     */
    private function checkRow($rawr)
    {

        $row['content'] = $rawr;
        $row['validationPass'] = [];
        foreach($row['content'] as $column=>$value){
            $nr = $this->checkColumn($column, $value);
            $row['content'][$column] = $nr['value'];
            $row['validationPass'] = array_merge($nr['validationPass'], $row['validationPass']);
        }
        return $row;
    }

    /**
     *
     * check a column
     *
     * @param $column
     * @param $value
     * @return array
     */
    private function checkColumn($column, $value){
        $nr = [
            'validationPass' => [],
            'value' => $value
        ];
        switch ($this->mf->colsDefinition[$column]['format']){
            case 'date':
                return $this->checkDate($column, $value);
                break;
            case 'string':
                return $this->checkString($column, $value);
                break;
            case 'numeric':
                return $this->checkNumeric($column, $value);
                break;
        }
        return $nr;
    }


    /**
     *
     * check if  we need to remove CRLF and if we need to add quotes
     *
     * @param $value
     * @return array
     */
    private function checkString($column, $value){
        $result = [
            'validationPass' => [$column => true],
            'value' => $value
        ];
        if(strpos($value, ',') !== false){
            $result['value'] = '"'.$value.'"';
        }
        return $result;
    }

    /**
     *
     * remove whitespace from resulting field
     *
     * @param $string
     * @return null|string|string[]
     */
    private function removeWhiteSpace($string){
        $string = preg_replace('/\s+/', '', $string);
        $string = str_replace(array("\r", "\n"), '', $string);
        return $string;
    }

    /**
     *
     * check if numeric
     * @param $column
     * @param $value
     * @return array
     */
    private function checkNumeric($column, $value){
        $result = [
            'validationPass' => [$column => true],
            'value' => $value
        ];
        if(!is_numeric($value)){
            $result['validationPass'] = [$column => false];
        }
        return $result;
    }

    /**
     *
     * date validation check
     *
     * @param $column
     * @param $value
     * @return array
     */
    private function checkDate($column, $value){
        $result = [
            'validationPass' => [$column => true],
            'value' => $value
        ];
        if (!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$value)) {
            $result = [
                'validationPass' => [$column => false],
                'value' => $value
            ];
        }
        $result['value'] =  \Carbon\Carbon::parse($result['value'])->format('d/m/Y');
        return $result;
    }


}