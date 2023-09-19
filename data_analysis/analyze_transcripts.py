from cmath import exp
from utilities import strip_utterance, remove_contractions, calc_lexical_richness
from nltk.tokenize import word_tokenize
from nltk.tokenize import sent_tokenize
from collections import Counter
from lexicalrichness import LexicalRichness
import click

class DataAnalysis:
    def __init__(self, lines):
        self.lines = lines

    def filter_B(self):
        """
        Filters and returns lines from the input's `lines` attribute that start with 'B: '. 
        The 'B: ' prefix is removed from the returned lines.

        Returns:
        - list of str: A list of lines that originally started with 'B: '.
        """
        filtered_lines = [line[3:] for line in self.lines if line.startswith('B: ')]
        return filtered_lines

    def count_words(self, lines):
        """
        Use NLTK word_tokenize to tokenize the input utterance (lines)
        Count the number of words in the provided line, excluding certain punctuations.
        
        Parameters:
        - lines (str): The utterance to be analyzed.

        Returns:
        - int: The number of words found in the provided lines, excluding '.', ',', or '?'.
        """
        words = word_tokenize(lines)
        words_stripped = []
        for char in words:
            if char not in ['.', ',', '?', '!']:
                words_stripped.append(char)
    
        return len(words_stripped)

    def count_unique_words(self, lines):
        """
        Use NLTK word_tokenize to tokenize the input utterance (lines)
        Count the number of unique words in the provided line, excluding certain punctuations.
        
        Parameters:
        - lines (str): The utterance to be analyzed.

        Returns:
        - int: The number of unique words found in the provided lines, excluding '.', ',', or '?'.
        """
        words = word_tokenize(lines)
        words_stripped = []
        for char in words:
            if char not in ['.', ',', '?', '!']:
                words_stripped.append(char.lower())
        unique_words = set(Counter(words_stripped))
        return len(unique_words)

    def utterance_length(self, lines):
        """
        Count the length of each utterance in the input dialogue, excluding certain punctuations.
        
        Parameters:
        - lines (str): The utterance to be analyzed.

        Returns:
        - int: The The length of each utterance, excluding '.', ',', or '?'.
        """
        utterances = sent_tokenize(lines)
        utterance_lentgth = [len(utterance) for utterance in utterances]
        return utterance_lentgth[0]

    def tokenize_line(self, lines):
        """
        Use NLTK word_tokenize a given line of text and remove specific punctuation marks.
        
        Parameters:
        - lines (str): The line of text to be tokenized.
        
        Returns:
        - list: A list of words from the line with specific punctuation marks ('.', ',', '?') removed.
        """
        words = word_tokenize(lines)
        words_stripped = []
        for char in words:
            if char not in ['.', ',', '?', '!']:
                words_stripped.append(char)

        return words_stripped

@click.command()
@click.option('--filename', type=click.Path(exists=True), prompt=True, help='Path to the file.')
@click.option('--explanation', '-exp', required=False, help='If --explanation=True, calculation formulas will be explained with output.')
def process_data(filename, explanation):
    global exp_needed
    exp_needed = explanation
    with open(filename, 'r') as file:
        lines = file.readlines()
    return lines

def calculate_lexical_diversity(lines, exp=False):
    """
    Calculate the lexical diversity of a list of filtered lines.
    
    Parameters:
    - lines (list): List of texts to be processed.
    - remove_contractions_func (function): A function to remove contractions from text.
    - dialogue.tokenize_line (function): Tokenizes the given string using NLTK word_tokenize.
    
    Returns:
    - float: Root Type-Token Ratio (RTTR) score.
    """
    plain_text = ""
    for line in remove_contractions(lines):
        words_per_line = dialogue.tokenize_line(line)
        utterance = ' '.join(words_per_line)
        plain_text += utterance + ' '
    
    score = calc_lexical_richness(plain_text)
    print(f"Root TTR Score: {score}")

    return score


def calculate_avg_words_per_utterance(lines, exp=False):
    """
    Calculate the average number of words per utterance for a list of tokenized lines.
    Tokenization and number of words/utterance count is done by the DataAnalysis Class
        
    Prints:
    - The average word count per utterance.
    - Formula explanation: (sum_of_avg_words_utterance/total_number_of_utterances).
    - Exact number of total words and total utterances.
    
    Returns:
    - None
    """
    total_words = 0
    for line in remove_contractions(lines):
        words_per_line = dialogue.count_words(line)
        total_words += words_per_line

    # Calculating Average Word count/utterance.    
    avg_word_count = total_words / len(lines) if lines else 0
    if exp:
        print(f"Avg. words/utterance: {round(avg_word_count,2)}.")
        print("(sum_of_avg_words_utterance/total_number_of_utterances).")
        print(f"In Exact Numbers: {total_words}/{len(lines)}.\n")

    return avg_word_count

def calculate_unique_words_score(lines, exp=False):
    """
    Calculate and print the average number of unique words per utterance for a list of tokenized lines.
    Tokenization and number of unique words/utterance count is done by the DataAnalysis Class
        
    Prints:
    - The average word count per utterance.
    - Formula explanation: (sum_of_unique_value/total_number_of_utterances).
    - Exact number of total unique words and total utterances.
    
    Returns:
    - None
    """
    total_unique_words = 0
    for line in remove_contractions(lines):
        unique_words_per_line = dialogue.count_unique_words(line)
        total_unique_words += unique_words_per_line
    
    #Calculating average unique words    
    average_unique_words = total_unique_words / len(lines) if lines else 0
    if exp:
        print(f"Avg. Unique words/utterance: words per utterance: {round(average_unique_words,2)}.")
        print("(sum_of_unique_value/total_number_of_utterances)")
        print(f"In Exact Numbers: {total_unique_words}/{len(lines)}.\n")

    return average_unique_words

def calculate_utterance_length(lines, exp=False):
    """
    Calculate and print the average utterance length for a list of filtered lines (based on characters).
    
    Process the list tokenized lines to determine the length 
    Stip each utterance from potential extra characters.
    Tokenize text with NLTK sent_tokenize.
    Calculate the average utterance length.
        
    Prints:
    - The average utterance length.
    - Formula explanation: (sum_of_utterance_length/total_number_of_utterances).
    - Exact number of total utterance length and total utterances.
    
    Returns:
    - None
    """
    sum_of_utterance_length = 0
    for utterance in lines:
        utterance_length = dialogue.utterance_length(strip_utterance(utterance))
        sum_of_utterance_length += utterance_length

    #Calculating the average utterance length
    avg_utterance_length = sum_of_utterance_length/len(lines) if lines else 0
    if exp:
        print(f"Avg. Utterance length: {round(avg_utterance_length,2)}.")
        print("(sum_of_utterance_length/total_number_of_utterances)")
        print(f"In Exact Numbers: {round(sum_of_utterance_length,2)}/{len(lines)}.\n")

    return avg_utterance_length

if __name__ == '__main__':
    lines = process_data(standalone_mode=False)
    dialogue = DataAnalysis(lines)
    filtered_lines = dialogue.filter_B()

    if exp_needed:
        # Execute calculations, with formulas being explained
        calculate_utterance_length(filtered_lines, True)
        calculate_avg_words_per_utterance(filtered_lines, True)
        calculate_unique_words_score(filtered_lines, True)
        calculate_lexical_diversity(filtered_lines, True)
    else:
        # Execute calculations without formulas being explained
        print(f"Utterance Length: {round(calculate_utterance_length(filtered_lines, False),3)}.")
        print(f"Average Words/utterance: {round(calculate_avg_words_per_utterance(filtered_lines, False),3)}.")
        print(f"Average Unique Words/utterance: {round(calculate_unique_words_score(filtered_lines, False),3)}.")
        print(f"Lexical Diversity: {round(calculate_lexical_diversity(filtered_lines, False),3)}")
