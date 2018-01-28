from scipy.io import wavfile
import numpy as np

import feature_mfccs_init
import feature_mfccs
import getDFT

eps = 0.00000001

"""
Extracts the audio features for the given audio file
"""


def file_feature_extraction(file, win=0.800, step=0.100, amplitude_filter=False, diff_filter=False):
    audio_info = wavfile.read(file)
    fs = audio_info[0]
    signal = audio_info[1]

    # Converting stereo signal to MONO signal
    if len(signal[0]) > 1:
        signal = np.float_(np.sum(signal, axis=1)) / 2

    # short-term feature extraction
    number_of_samples = len(signal)
    duration = np.float_(number_of_samples) / fs  # in seconds

    # convert window length and step from seconds to samples
    window_length = np.int(np.round(win * fs))
    step_in_samples = np.int(np.round(step * fs))

    # Total frames is one since we're slicing the file in main.py
    num_of_frames = 1

    # number of features to be computed:
    num_of_features = 21
    features = np.zeros((num_of_frames, num_of_features))

    # Frequency-domain audio features
    ham = np.hamming(window_length)
    mfcc_params = feature_mfccs_init.feature_mfccs_init(window_length, fs)

    win = np.int(window_length)
    nfft = int(win / 2)

    cur_pos = 1

    ampl_vals = []
    diff_vals = []

    for i in range(0, num_of_frames):
        # get current frame:\
        frame = signal[cur_pos - 1: cur_pos + window_length - 1]
        if i == 0:
            frameprev = frame.copy()

        ampl_val = np.max(frame)
        ampl_vals.append(ampl_val)

        diff_val = np.subtract(frameprev, frame)
        diff_vals.append(np.mean(diff_val))

        frameprev = frame.copy()

        try:
            frame = frame * ham

        # Hacky handling of incorrect dot-product dimensions
        #   Occurs because of remainders when handling steps, frames, etc.
        except ValueError:
            ham = ham[:-1]
            frame = frame * ham


        frameFFT = getDFT.getDFT(frame, fs)

        X = np.abs(np.fft.fft(frame))
        X = X[0:nfft]  # normalize fft
        X = X / len(X)

        if i == 0:
            Xprev = X.copy()

        if np.sum(np.abs(frame)) > np.spacing(1):
            try:
                MFCCs = feature_mfccs.feature_mfccs(frameFFT, mfcc_params)

            # Hacky handling of incorrect dot-product dimensions
            except ValueError:
                frameFFT = frameFFT[:-1]
                MFCCs = feature_mfccs.feature_mfccs(frameFFT, mfcc_params)

            features[i][0:13] = MFCCs
        else:
            features[:, i] = np.zeros(num_of_features, 1)

        features[i][13] = stEnergy(frame)
        features[i][14] = stZCR(frame)
        features[i][15] = stEnergyEntropy(frame)
        [features[i][16], features[i][17]] = stSpectralCentroidAndSpread(X, fs)
        features[i][18] = stSpectralEntropy(X)
        features[i][19] = stSpectralRollOff(X, 0.90, fs)

        cur_pos = cur_pos + step_in_samples
        frameFFTPrev = frameFFT
        Xprev = X.copy()

    ampl_threshold = np.percentile(ampl_vals, 93)
    diff_threshold = np.percentile(diff_vals, 80)

    for i in range(0, num_of_frames):
        if amplitude_filter and ampl_vals[i] < ampl_threshold:
            features[i][20] = 1.0
        elif diff_filter and diff_vals[i] > diff_threshold:
            features[i][20] = 1.0
        else:
            features[i][20] = 0.0

    return features


# Spectral Centroid and Spread
def stSpectralCentroidAndSpread(x, fs):
    """Computes spectral centroid of frame (given abs(FFT))"""

    ind = (np.arange(1, len(x) + 1)) * (fs / (2.0 * len(x)))

    xt = x.copy()
    xt = xt / xt.max()
    num = np.sum(ind * xt)
    den = np.sum(xt) + eps

    # Centroid:
    c = (num / den)

    # Spread:
    s = np.sqrt(np.sum(((ind - c) ** 2) * xt) / den)

    # Normalize:
    c /= (fs / 2.0)
    s /= (fs / 2.0)

    return c, s


# Spectral Entropy
def stSpectralEntropy(x, num_of_short_blocks=10):
    """Computes the spectral entropy"""
    L = len(x)  # number of frame samples
    Eol = np.sum(x ** 2)  # total spectral energy

    sub_win_length = np.int(np.floor(L / num_of_short_blocks))  # length of sub-frame
    if L != sub_win_length * num_of_short_blocks:
        x = x[0:sub_win_length * num_of_short_blocks]

    # define sub-frames (using matrix reshape)
    sub_windows = x.reshape(sub_win_length, num_of_short_blocks, order='F').copy()
    # compute spectral sub-energies
    s = np.sum(sub_windows ** 2, axis=0) / (Eol + eps)
    # compute spectral entropy
    en = -np.sum(s * np.log2(s + eps))

    return en


# Spectral Flux
def stSpectralFlux(x, xprev):
    # compute the spectral flux as the sum of square distances:
    sum_x = np.sum(x + eps)
    sum_prev_x = np.sum(xprev + eps)
    f = np.sum((x / sum_x - xprev / sum_prev_x) ** 2)

    return f


# Spectral Rolloff
def stSpectralRollOff(X, c, fs):
    """Computes spectral roll-off"""
    totalEnergy = np.sum(X ** 2)
    fftLength = len(X)
    Thres = c * totalEnergy
    # Find the spectral rolloff as the frequency position where the respective spectral energy is equal to c*totalEnergy
    CumSum = np.cumsum(X ** 2) + eps
    [a, ] = np.nonzero(CumSum > Thres)
    if len(a) > 0:
        mC = np.float64(a[0]) / (np.float(fftLength))
    else:
        mC = 0.0
    return (mC)


# Time-domain audio features
# Energy feature extraction
def stEnergy(frame):
    """Computes signal energy of frame"""
    return np.sum(frame ** 2) / np.float64(len(frame))


# Zero crossing rate feature extraction
def stZCR(frame):
    """Computes zero crossing rate of frame"""
    count = len(frame)
    countZ = np.sum(np.abs(np.diff(np.sign(frame)))) / 2
    return (np.float64(countZ) / np.float64(count - 1.0))


# Energy Entropy
def stEnergyEntropy(frame, numOfShortBlocks=10):
    """Computes entropy of energy"""
    Eol = np.sum(frame ** 2)  # total frame energy
    L = len(frame)
    subWinLength = np.int(np.floor(L / numOfShortBlocks))
    if L != subWinLength * numOfShortBlocks:
        frame = frame[0:subWinLength * numOfShortBlocks]
    # subWindows is of size [numOfShortBlocks x L]
    subWindows = frame.reshape(subWinLength, numOfShortBlocks, order='F').copy()

    # Compute normalized sub-frame energies:
    s = np.sum(subWindows ** 2, axis=0) / (Eol + eps)

    # Compute entropy of the normalized sub-frame energies:
    entropy = -np.sum(s * np.log2(s + eps))
    return entropy
