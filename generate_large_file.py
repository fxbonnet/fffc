import os
from pathlib import Path
import argparse


def generate_large_input(target_size: float, path_model: str, path_computed_input: str):
    """Create an input file of the wanted size by concatenating a model, keeping the same metadata structure"""
    path_model, path_computed_input, target_size = Path(path_model), Path(path_computed_input), int(target_size)
    model_size = os.path.getsize(path_model)
    assert target_size > model_size, "The target size should be greater than the size of your model."

    with open(path_computed_input, 'w+', encoding='utf8', newline='\n') as input_file:
        with open(path_model, 'r', encoding='utf8') as model_file:
            for line in model_file:
                if line[-1] != '\n':  # Adding a new line to the last line of the model.
                    line = line + '\n'
                for i in range(target_size // model_size + 1):
                    input_file.write(line)


def get_args():
    """Read the arguments from the command line."""
    parser = argparse.ArgumentParser()
    parser.add_argument('-s',
                        '--target_size',
                        action="store",
                        dest="target_size",
                        type=int)

    parser.add_argument('-m',
                        '--path_model',
                        action="store",
                        dest="path_model",
                        type=str)

    parser.add_argument('-o',
                        '--path_output',
                        action="store",
                        dest="path_input",
                        type=str)

    return parser.parse_args()


if __name__ == "__main__":
    args = get_args()
    generate_large_input(target_size=args.target_size,
                         path_model=args.path_model,
                         path_computed_input=args.path_input)
