import argparse


def get_args():
    parser = argparse.ArgumentParser()
    parser.add_argument('-i',
                        '--input_path',
                        action="store",
                        dest="input_path",
                        type=str,
                        default='input.txt',
                        help="Path towards the file to be converted.")

    parser.add_argument('-m',
                        '--meta_path',
                        action="store",
                        dest="meta_path",
                        type=str,
                        default='meta.txt',
                        help="Path towards the metadata file.")

    parser.add_argument('-o',
                        '--output_path',
                        action="store",
                        dest="output_path",
                        type=str,
                        default='output.txt',
                        help="Path towards the output, converted, csv file to be created.")

    return parser.parse_args()
