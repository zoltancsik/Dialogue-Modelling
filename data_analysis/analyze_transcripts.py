from nltk.tokenize import word_tokenize
from nltk.tokenize import sent_tokenize
from collections import Counter
from lexicalrichness import LexicalRichness
import click
import re

@click.command()
@click.option('--filename', type=click.Path(exists=True), prompt=True, help='Path to the file.')

def process_data(filename):
    with open(filename, 'r') as file:
        lines = file.readlines()
    return lines

class DataAnalysis:
    def __init__(self, lines):
        self.lines = lines

    def filter_B(self):
        """
        Filters and returns lines from the instance's `lines` attribute that start with 'B: '. 
        The 'B: ' prefix is removed from the returned lines.

        Returns:
        - list of str: A list of lines that originally started with 'B: '.
        """
        filtered_lines = [line[3:] for line in self.lines if line.startswith('B: ')]
        return filtered_lines

    def count_avg_words(self, lines):
        """
        Count the number of words in the provided lines, excluding certain punctuations.
        
        Parameters:
        - lines (str): The sentence to be analyzed.

        Returns:
        - int: The number of words found in the provided lines, excluding '.', ',', or '?'.
        """
        words = word_tokenize(lines)
        words_stripped = []
        for char in words:
            if char not in ['.', ',', '?']:
                words_stripped.append(char)
    
        return len(words_stripped)

    def count_unique_words(self, lines):
        """
        Count the number of unique words in the provided lines, excluding certain punctuations.
        
        Parameters:
        - lines (str): The sentence to be analyzed.

        Returns:
        - int: The number of unique words found in the provided lines, excluding '.', ',', or '?'.
        """
        words = word_tokenize(lines)
        words_stripped = []
        for char in words:
            if char not in ['.', ',', '?']:
                words_stripped.append(char)
        unique_words = set(Counter(words_stripped))
        return len(unique_words)

    def sentence_length(self, lines):
        """
        Count the length of each sentence in the input dialogue, excluding certain punctuations.
        
        Parameters:
        - lines (str): The sentence to be analyzed.

        Returns:
        - int: The The length of each sentence, excluding '.', ',', or '?'.
        """
        sentences = sent_tokenize(lines)
        sentence_lentgth = [len(sentence) for sentence in sentences]
        return sentence_lentgth[0]

def calculate_avg_words_per_sentence():
    total_words = 0
    for line in remove_contractions(filtered_lines):
        words_per_line = dialogue.count_avg_words(line)
        total_words += words_per_line
        
    avg_word_count = total_words / len(filtered_lines) if filtered_lines else 0
    print(f"Avg. words/Sentence: {round(avg_word_count,2)}.")
    print("(sum_of_avg_words_sentence/total_number_of_sentences).")
    print(f"In Exact Numbers: {total_words}/{len(filtered_lines)}.\n")

def calculate_unique_words_score():
    total_unique_words = 0
    for line in remove_contractions(filtered_lines):
        unique_words_per_line = dialogue.count_unique_words(line)
        total_unique_words += unique_words_per_line
        
    average_unique_words = total_unique_words / len(filtered_lines) if filtered_lines else 0
    print(f"Avg. Unique words/Sentence: words per sentence: {round(average_unique_words,2)}.")
    print("(sum_of_unique_value/total_number_of_sentences)")
    print(f"In Exact Numbers: {total_unique_words}/{len(filtered_lines)}.\n")

def calculate_sentence_length():
    sum_of_sentence_length = 0
    for sentence in filtered_lines:
        sentence_length = dialogue.sentence_length(strip_sentence(sentence))
        sum_of_sentence_length += sentence_length

    #Calculating the average sentence length
    avg_sentence_length = sum_of_sentence_length/len(filtered_lines)
    print(f"Avg. Sentence length: {round(avg_sentence_length,2)}.")
    print("(sum_of_sentence_length/total_number_of_sentences)")
    print(f"In Exact Numbers: {round(sum_of_sentence_length,2)}/{len(filtered_lines)}.\n")

def strip_sentence(sentence):
    # Use regular expression to remove spaces and special characters
    cleaned_sentence = re.sub(r'[^A-Za-z0-9]+', '', sentence)
    return cleaned_sentence

def remove_contractions(line):
    # Initialize an empty list to store the modified lines
    modified_lines = []

    # Define a list of replacement patterns and their corresponding replacements
    patterns_and_replacements = [
        (r"'s", "s"),
        (r"'t", "t"),
        (r"'d", "d"),
        (r"'v", "v")  # Replace all possible contractions
    ]
    # Iterate through the text
    for l in line:
        for pattern, replacement in patterns_and_replacements:
            l = re.sub(pattern, replacement, l)
        modified_lines.append(l)

    return(modified_lines)

if __name__ == "__main__":
    lines = process_data(standalone_mode=False)
    dialogue = DataAnalysis(lines)
    filtered_lines = dialogue.filter_B()

    # Call the calculations
    calculate_sentence_length()
    calculate_avg_words_per_sentence()
    calculate_unique_words_score()
