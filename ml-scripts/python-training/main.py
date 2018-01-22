__author__ = 'Shalini Ramachandra'

from file_feature_extraction import file_feature_extraction


def createArff(features, labels):
    arff = generateArffHeader()
    for x in range(len(features)):
        arff += (' '.join(str(val) for val in features[x]) + " " + labels[x] + "\n")

    return arff


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

        @data\n\n
    """

    return header


def execute():
    labels = []
    feature_array = []
    for line in laughter_file:
        line = line.strip("\n")
        feature_array.append(file_feature_extraction(line))
        labels.append('YES')

    for line in non_laughter_file:
        line = line.strip("\n")
        feature_array.append(file_feature_extraction(line))
        labels.append('NO')

    return createArff(feature_array, labels)


if __name__ == '__main__':
    # TODO: How is the service going to supply a dict of videos and their timestamps?
    instancesPerVideo = {}
    execute()
