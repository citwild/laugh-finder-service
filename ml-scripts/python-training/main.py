
__author__ = 'Shalini Ramachandra'

import argparse
import ujson
from pydub import AudioSegment
from io import BytesIO

from file_feature_extraction import file_feature_extraction
from get_azure_blob import get_wav_file


def generateArffHeader():
    header = """
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

@data\n
"""

    return header


def createArff(features, labels):
    arff = generateArffHeader()
    for x in range(len(features)):
        # Uses features[x][0] because only searching a single value
        sample = (' '.join(str(round(val, 11)) for val in features[x][0]) + " " + labels[x] + "\n")
        arff += sample

    return arff


def execute(json_input):

    videos = parse_json(json_input)

    labels = []  # for correctness
    feature_array = []  # for features

    # for each video, get features and mark correctness
    for video in videos:

        # Get audio from store
        audio_file = get_wav_file(video['bucket'], video['key'])
        audio_file = AudioSegment.from_wav(audio_file)

        # For each sample in audio file, process and append to ARFF
        for sample in video['instances']:
            # grab sample and export into byte string
            audio_sample = BytesIO()
            audio_file[
                int(sample['start'] * 1000)
                :int(sample['stop'] * 1000)
            ].export(
                audio_sample,
                format='wav'
            )

            feature_array.append(
                file_feature_extraction(
                    # re_read_file,
                    BytesIO(audio_sample.getvalue()),
                    win=(sample['stop'] - sample['start'])  # set custom window
                )
            )
            labels.append('YES' if sample['correct'] == "Y" else 'NO')

    return createArff(feature_array, labels)


# Parses input JSON into videos and their timestamp samples
def parse_json(input_json):
    return ujson.loads(input_json)['files']


"""
Webservice will call this function, providing a JSON of videos and their timestamps to search.

{
    files: [
        {
            key: string
            bucket: string
            instances: [
                {
                    start: float
                    stop: float
                    correct: Y or N
                }
            ]
        }
    ]
}
"""
if __name__ == '__main__':
    # Get JSON of samples
    parser = argparse.ArgumentParser(description='Arguments for generating a re-trained ARFF model.')
    parser.add_argument('--samples', dest='samples', help="All retraining samples in a JSON string.", required=True)
    args = parser.parse_args()

    # Execute
    execute(args.samples)
