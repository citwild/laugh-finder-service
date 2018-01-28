from math import *
from numpy import *


def feature_mfccs(window_fft, mfcc_params):
    # This function computes the mfccs using the provided DFT.
    # The parameters (DCT, filter banks, etc) need to have been
    # computed using the feature_mfccs_init function.
    #
    # ARGUMENTS:
    # - windowFFT:              the abs(FFT) of an audio frame
    #                           (computed by getDFT() function)
    # - mfccParams:             algorithm parameters, as returned
    #                   by feature_mfccs_init()
    #
    # RETURNS:
    # -ceps:            MFCC matrix (row i corresponds to
    #                   the i-th MFCC feature sequence)
    #
    # Based on Slaneys' Auditory Toolbox
    # https://engineering.purdue.edu/~malcolm/interval/1998-010/

    ear_mag = log10(
        dot(
            mfcc_params['mfccFilterWeights'],
            window_fft
        ) + spacing(1)
    )

    ceps = dot(
        mfcc_params['mfccDCTMatrix'],
        ear_mag
    )
    return ceps
