import re

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
        (r"'m", "m"),
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
