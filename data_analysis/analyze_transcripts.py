from utilities import strip_sentence, remove_contractions
from nltk.tokenize import word_tokenize
from nltk.tokenize import sent_tokenize
from collections import Counter
from lexicalrichness import LexicalRichness
import click

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
        Filters and returns lines from the input's `lines` attribute that start with 'B: '. 
        The 'B: ' prefix is removed from the returned lines.

        Returns:
        - list of str: A list of lines that originally started with 'B: '.
        """
        filtered_lines = [line[3:] for line in self.lines if line.startswith('B: ')]
        return filtered_lines

    def count_words(self, lines):
        """
        Use NLTK word_tokenize to tokenize the input sentence (lines)
        Count the number of words in the provided line, excluding certain punctuations.
        
        Parameters:
        - lines (str): The sentence to be analyzed.

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
        Use NLTK word_tokenize to tokenize the input sentence (lines)
        Count the number of unique words in the provided line, excluding certain punctuations.
        
        Parameters:
        - lines (str): The sentence to be analyzed.

        Returns:
        - int: The number of unique words found in the provided lines, excluding '.', ',', or '?'.
        """
        words = word_tokenize(lines)
        words_stripped = []
        for char in words:
            if char not in ['.', ',', '?', '!']:
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

def calculate_lexical_diversity(lines):
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
        sentence = ' '.join(words_per_line)
        plain_text += sentence + ' '
    
    lex = LexicalRichness(plain_text)
    root_ttr_score = lex.rttr
    print(f"Root TTR Score: {root_ttr_score}")

    return root_ttr_score


def calculate_avg_words_per_sentence(lines):
    """
    Calculate the average number of words per sentence for a list of tokenized lines.
    Tokenization and number of words/sentence count is done by the DataAnalysis Class
        
    Prints:
    - The average word count per sentence.
    - Formula explanation: (sum_of_avg_words_sentence/total_number_of_sentences).
    - Exact number of total words and total sentences.
    
    Returns:
    - None
    """
    total_words = 0
    for line in remove_contractions(lines):
        words_per_line = dialogue.count_words(line)
        total_words += words_per_line

    # Calculating Average Word count/sentence.    
    avg_word_count = total_words / len(lines) if lines else 0
    # print(f"Avg. words/Sentence: {round(avg_word_count,2)}.")
    # print("(sum_of_avg_words_sentence/total_number_of_sentences).")
    # print(f"In Exact Numbers: {total_words}/{len(lines)}.\n")

    return avg_word_count

def calculate_unique_words_score(lines):
    """
    Calculate and print the average number of unique words per sentence for a list of tokenized lines.
    Tokenization and number of unique words/sentence count is done by the DataAnalysis Class
        
    Prints:
    - The average word count per sentence.
    - Formula explanation: (sum_of_unique_value/total_number_of_sentences).
    - Exact number of total unique words and total sentences.
    
    Returns:
    - None
    """
    total_unique_words = 0
    for line in remove_contractions(lines):
        unique_words_per_line = dialogue.count_unique_words(line)
        total_unique_words += unique_words_per_line
    
    #Calculating average unique words    
    average_unique_words = total_unique_words / len(lines) if lines else 0
    # print(f"Avg. Unique words/Sentence: words per sentence: {round(average_unique_words,2)}.")
    # print("(sum_of_unique_value/total_number_of_sentences)")
    # print(f"In Exact Numbers: {total_unique_words}/{len(lines)}.\n")

    return average_unique_words

def calculate_sentence_length(lines):
    """
    Calculate and print the average sentence length for a list of filtered lines (based on characters).
    
    Process the list tokenized lines to determine the length 
    Stip each sentence from potential extra characters.
    Tokenize text with NLTK sent_tokenize.
    Calculate the average sentence length.
        
    Prints:
    - The average sentence length.
    - Formula explanation: (sum_of_sentence_length/total_number_of_sentences).
    - Exact number of total sentence length and total sentences.
    
    Returns:
    - None
    """
    sum_of_sentence_length = 0
    for sentence in lines:
        sentence_length = dialogue.sentence_length(strip_sentence(sentence))
        sum_of_sentence_length += sentence_length

    #Calculating the average sentence length
    avg_sentence_length = sum_of_sentence_length/len(lines) if lines else 0
    # print(f"Avg. Sentence length: {round(avg_sentence_length,2)}.")
    # print("(sum_of_sentence_length/total_number_of_sentences)")
    # print(f"In Exact Numbers: {round(sum_of_sentence_length,2)}/{len(lines)}.\n")

    return avg_sentence_length

if __name__ == "__main__":
    lines = process_data(standalone_mode=False)
    dialogue = DataAnalysis(lines)
    filtered_lines = dialogue.filter_B()

    # Call the calculations
    print(f"Sentence Length: {round(calculate_sentence_length(filtered_lines),3)}.")
    print(f"Average Words/Sentence: {round(calculate_avg_words_per_sentence(filtered_lines),3)}.")
    print(f"Average Unique Words/Sentence: {round(calculate_unique_words_score(filtered_lines),3)}.")
    print(f"Lexical Diversity: {round(calculate_lexical_diversity(filtered_lines),3)}")

