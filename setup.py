from setuptools import setup, find_packages
import fffc

with open('requirements.txt') as fh:
    requires = [requirement.strip() for requirement in fh]

setup(
    name='fffc',
    version=fffc.__version__,
    packages=find_packages(exclude=["*.tests", "*.tests.*", "tests.*", "tests"]),
    setup_requires=["pytest-runner"],
    tests_require=["pytest"],
    url='',
    license=open("LICENSE.txt").read(),
    author=fffc.__author__,
    author_email='kobuskc@gmail.com',
    include_package_data=True,
    zip_safe=False,
    scripts=['bin/fffc'],
    description=''
)
