import unittest
import main as uut
import logging.config

"""
Test the main python script
"""


class MainTest(unittest.TestCase):
    logging.config.fileConfig("../resources/logging.conf")
    log = logging.getLogger("logger")

    headerOutput = """% Title: MFCC, Energy, Zero Crossing rate Features
    %
    % Creator: Shalini Ramachandra
    % Date: 10/30/2015
    %
    @relation Laughter_detection_capture_training

    @attribute MFCC1 NUMERIC
    @attribute MFCC2 NUMERIC
    @attribute MFCC3 NUMERIC
    @attribute MFCC4 NUMERIC
    @attribute MFCC5 NUMERIC
    @attribute MFCC6 NUMERIC
    @attribute MFCC7 NUMERIC
    @attribute MFCC8 NUMERIC
    @attribute MFCC9 NUMERIC
    @attribute MFCC10 NUMERIC
    @attribute MFCC11 NUMERIC
    @attribute MFCC12 NUMERIC
    @attribute MFCC13 NUMERIC
    @attribute ENERGY NUMERIC
    @attribute ZCR NUMERIC
    @attribute ENERGY_ENTROPY NUMERIC
    @attribute SPECTRAL_CENTROID NUMERIC
    @attribute SPECTRAL_SPREAD NUMERIC
    @attribute SPECTRAL_ENTROPY NUMERIC
    @attribute SPECTRAL_ROLLOFF NUMERIC
    @attribute class {YES,NO}

    @data"""

    # Should create the expected header
    # def test_generateArffHeader_1(self):
    #     self.log.info("Testing format of Weka file header")
    #     self.assertEquals(uut.generateArffHeader(), self.headerOutput)

    # Should read a
    def test_parse_json_1(self):
        input = '''
            {
                "files": [
                    {
                        "key": "lfassets",
                        "bucket": "video/some1.mp4",
                        "instances": [
                            {
                                "start": 10.200,
                                "stop": 11.000,
                                "correct": "Y"
                            }
                        ]
                    },
                    {
                        "key": "lfassets",
                        "bucket": "video/some2.mp4",
                        "instances": [
                            {
                                "start": 8.000,
                                "stop": 8.800,
                                "correct": "N"
                            }
                        ]
                    }
                ]
            }
        '''
        result = uut.parse_json(input)

        self.assertEqual("lfassets", result[0]['key'])
        self.assertEqual("video/some2.mp4", result[1]['bucket'])
        self.assertEqual(10.200, result[0]['instances'][0]['start'])

    # Should produce expected output
    def test_createArff_1(self):
        print(
            uut.createArff(
                [
                    [1, 2, 3, 4, 5, 6, 7, 8, 9],
                    [1, 2, 3, 4, 5, 6, 7, 8, 9]
                ],
                [
                    'YES',
                    'NO'
                ]
            )
        )
