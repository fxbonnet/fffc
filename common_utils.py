"""module containing common methods used across all modules"""
import time


def timing(func):
    """decorator to time and log execution time of a method

    :param func: the method to time
    :return: wrapper over the method to time
    """

    def wrap(*args):
        time1 = time.time()
        ret = func(*args)
        time2 = time.time()
        print('{:s} function took {:.3f} ms'.format(func.__name__, (time2-time1)*1000.0))

        return ret
    return wrap
