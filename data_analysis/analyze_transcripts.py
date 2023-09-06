class DataAnalysis:
    def __init__(self, filename):
        self.filename = filename

    def filter_B(self):
        """
        Filters the lines from the file specified in self.filename to only include lines of the dialogue
        that start with 'B: '. The returned lines exclude the 'B: ' prefix, creating data that can be analyzed.

        Returns:
            list: A list of filtered lines from the dialogue without the 'B: ' prefix.
        """
        with open(self.filename, 'r') as file:
            lines = file.readlines()
            filtered_lines = [line[3:] for line in lines if line.startswith('B: ')]
        return filtered_lines

if __name__ == "__main__":
    dialogue = DataAnalysis('../transcribed_data/HC1.txt')
    
    filtered_lines = dialogue.filter_B()
    for line in filtered_lines:
        print(line, end='')
