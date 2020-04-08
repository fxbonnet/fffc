# Fixed File Format converter

## Passing arguments via Command Line 

The CLI takes 3 arguments
* --input_path or -i (string, default 'input.txt'): path towards the input file.
* --meta_path or -m (string, default 'meta.txt'): path towards the metadata file.
* --output_path or -o (string, default 'output.csv'): path towards the output file (if it does not exist, il will be created).

Accordingly, a valid call is ```python file_converter.py -i path/to/input.txt -o output.csv -m meta.txt```

## Execution Environnement

This project is coded in Python 3.7.3. If you can't run it on your Python3, then install the dependencies with ```pip install -r requirements.txt ```

### Example
To run the training, the user would for example type :
```
python train.py --mode 1 --delta 2 --nb_cycles 1
```
This would provide a training of major triads. The user would be displayed all 12 major triads once and would have to play one every 2 seconds.

## Prerequisites

To make sure you have all the dependencies required in this project, run



## Contributing

If you're a piano jazz player and feel like contributing, that's great! Contact me to discuss feature enriching / fix. Any comment on my code is more than welcomed.

## Acknowledgments

I started this project in April 2019 to help me learn & memorize jazz chords and voicings. Hope it can help you too!
