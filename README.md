# FurHat Skill

## Overview
This skill provides an interactive conversation flow for the Furhat robot centered around cinema experiences, both past and recent. It guides the user through questions about their latest cinema visits, while serving the purpose of collecting dialogue data for future analyses.

## Example States:

1. **LastMovieWatched** - Open question, no intent recognition.
2. **AskGenreState** - SlotFilling state with intent recognition.
3. **SeenMovieState** - Yes or no question.

## Usage:

- Ensure you have the Furhat SDK and necessary dependencies installed.
- For Further Information, see: https://docs.furhat.io/

# Dialogue Data Analysis Tool

## Overview
This tool is used to analyze the transcribed dialogue data. Main features include analyzing lexical richness, counting words, identifying unique words, and determining utterance lengths.

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
To validate the formulas used in the data_analysis script, a test unit named test_calulations.py was written
The test functions aim to validate the primary functions of the `DataAnalysis` class and associated utilities.

## Dependencies:
- **analyze_transcripts**: The primary module containing the `DataAnalysis` class.
- **utilities**: Contains helper functions for text analysis, such as `strip_utterance`, `remove_contractions`, and `calc_lexical_richness`.
- **lexicalrichness**: A library for computing lexical richness scores.

## Output:
```Console
=============================================================================================== test session starts ===============================================================================================
platform linux -- Python 3.10.12, pytest-7.4.1, pluggy-1.3.0
rootdir: xyz
collected 7 items                                                                                                                                                                                                 

test_calculations.py .......                                                                                                                                                                                [100%]

================================================================================================ 7 passed in 1.13s ================================================================================================
```
# Dialogue Data Evaluation

## Overview

The "Dialogue Data Evaluation" R Markdown aims to evaluate linguistic dialogue features extracted from the analysed data. 

## Key Features

- **Data Preparation**: Creation of a data frame that encapsulates linguistic dialogue features.
  
- **Divided Dialogues**: Splits the initial dataset into separate Human-Human and Human-Computer datasets for more focused analysis.

- **Mean Value Calculation**: Computes the mean value for each linguistic feature across multiple dialogues, both for Human-Human and Human-Computer datasets.

- **Combined Mean Values**: Aggregates the mean values from both datasets into a summary data frame for further analysis.

- **Visual Comparison**: A graphical representation comparing the HH and HC data using a bar graph.

- **Statistical Relevance**: Application of the Two-sample independent t-test to determine if there's any significant difference between Human-Human and Human-Computer dialogue metrics.

## Requirements

1. Ensure you have the required R libraries installed:
   - knitr
   - ggplot2
