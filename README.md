# FurHat Skill
## Cinema Conversation Script for Furhat

## Overview
This skill provides an interactive conversation flow for the Furhat robot centered around cinema experiences, both past and recent. It guides the user through questions about their latest cinema visits, their opinions on movies, and their reflections on the general state of cinemas today.

## Example States:

1. **LastMovieWatched** - Open question, no intent recognition.
2. **AskGenreState** - SlotFilling state with intent recognition.
3. **SeenMovieState** - Yes or no question.

## Usage:

- Ensure you have the Furhat SDK and necessary dependencies installed.
- For Further Information, see: https://docs.furhat.io/

# Dialogue Data Analysis Tool

## Overview
This tool provides various utilities for analyzing text data, particularly dialogue lines. It leverages the NLTK library for text processing and tokenization. Main features include analyzing lexical richness, counting words, identifying unique words, and determining utterance lengths.

## Modules and Classes:
- **DataAnalysis**: Contains methods to filter and analyze lines of text.
    - `filter_B()`: Filters lines starting with 'B: (participants' utterances)'.
    - `count_words(lines)`: Counts words in a given line, excluding specific punctuations.
    - `count_unique_words(lines)`: Counts unique words in a given line, excluding specific punctuations.
    - `utterance_length(lines)`: Counts the length of each utterance.
    - `tokenize_line(lines)`: Tokenizes a line of text and removes specific punctuations.

## Utility Functions:
- `calculate_lexical_diversity(lines, exp=False)`: Computes the lexical diversity of a list of lines.
- `calculate_avg_words_per_utterance(lines, exp=False)`: Calculates the average word count per utterance.
- `calculate_unique_words_score(lines, exp=False)`: Computes the average number of unique words per utterance.
- `calculate_utterance_length(lines, exp=False)`: Determines the average utterance length for a list of lines.

## Usage:
1. Install dependencies: Ensure `NLTK` and `click` libraries are installed.
2. Execute the script: 
   ```bash
   python <script_name>.py --filename <path_to_file> [--explanation True]
   ```
   The `--explanation` flag will provide detailed formula explanations with the output.

## Dependencies:
- NLTK: Used for tokenizing the text.
- Click: Used for creating a command-line interface.
- Other custom utilities as imported: `strip_utterance`, `remove_contractions`, `calc_lexical_richness`.

## Example Output:

```Console
Utterance Length: 33.372.
Average Words/utterance: 8.721.
Average Unique Words/utterance: 8.093.
Root TTR Score: 9.617831033907356
Lexical Diversity: 9.618
```
# Testing

## Overview:
The test functions aim to validate the primary functions of the `DataAnalysis` class and associated utilities.


## Dependencies:
- **analyze_transcripts**: The primary module containing the `DataAnalysis` class.
- **utilities**: Contains helper functions for text analysis, such as `strip_utterance`, `remove_contractions`, and `calc_lexical_richness`.
- **lexicalrichness**: A library for computing lexical richness scores.
