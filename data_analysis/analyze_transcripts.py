from nltk.tokenize import word_tokenize
from collections import Counter
import click

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

def calculate_avg_words_per_sentence():
    total_words = 0
    for line in filtered_lines:
        words_per_line = dialogue.count_avg_words(line)
        total_words += words_per_line
        
    avg_word_count = total_words / len(filtered_lines) if filtered_lines else 0
    print(f"Average number of words per sentence: {round(avg_word_count,2)}.")
    print(f"Calculation: sum_of_avg_words_sentence/total_number_of_sentences.")
    print(f"In Exact Numbers: {avg_word_count}/{len(filtered_lines)}.")

def calculate_unique_words_score():
    total_unique_words = 0
    for line in filtered_lines:
        unique_words_per_line = dialogue.count_unique_words(line)
        total_unique_words += unique_words_per_line
        
    average_unique_words = total_unique_words / len(filtered_lines) if filtered_lines else 0
    print(f"Average number of unique words per sentence: {round(average_unique_words,2)}.")
    print(f"Calculation: sum_of_unique_value/total_number_of_sentences.")
    print(f"In Exact Numbers: {total_unique_words}/{len(filtered_lines)}.")

@click.command()
@click.option('--filename', type=click.Path(exists=True), prompt=True, help='Path to the file.')
def process_data(filename):
    """Data Analysis script"""
    with open(filename, 'r') as file:
        lines = file.readlines()
    return lines

if __name__ == "__main__":
    lines = process_data(standalone_mode=False)
    dialogue = DataAnalysis(lines)
    filtered_lines = dialogue.filter_B()

    # Call the calculations
    calculate_avg_words_per_sentence()
    calculate_unique_words_score()
