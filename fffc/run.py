import os
import sys
sys.path.insert(0, os.path.abspath('..'))

from fffc import fffc

if __name__ == '__main__':
    client = fffc.fffc()
    client.run()
