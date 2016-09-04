import boto3, botocore
import wave

# ACCESS_KEY = 'AKIAJ2P2LBUOUCUOCLHA'
# SECRET_KEY = '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'

# url = 'https://s3-us-west-2.amazonaws.com/beamcoffer/ExtractedAudio/Compressed/2014-01-31/Huddle/00079-320.wav'

# bucket_name = 'beamcoffer'
# key = 'ExtractedAudio/Compressed/2014-02-19/PS E/GoPro/GP050018-320.wav'

def get_wav_file(bucket_name, key):
    # get resource
    s3 = boto3.resource('s3')

    # access bucket
    object = s3.Object(bucket_name, key)
    result = object.get()['Body'].read()

    return result
