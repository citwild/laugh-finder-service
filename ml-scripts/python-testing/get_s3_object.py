import boto3, botocore
import wave


def get_wav_file(bucket_name, key):
    # get resource
    s3 = boto3.resource('s3')

    # access bucket
    object = s3.Object(bucket_name, key)
    result = object.get()['Body'].read()

    return result
