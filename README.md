# ImageToText

A professional Java Swing application for converting images to text using OCR, with a user-friendly interface and persistent storage of past conversions.

## Features
- **Home Menu** with two options:
  - Image to Text Conversion
  - Past Conversions
- **Drag-and-drop** image area for easy image selection
- **Text extraction** from images using Tesseract OCR (via Tess4J)
- **Persistent storage** of all past conversions
- **Simple, modern GUI**

## Setup Instructions

### Prerequisites
- Java 8 or higher
- [Tesseract OCR](https://github.com/tesseract-ocr/tesseract) installed on your system
- Tess4J library (Java wrapper for Tesseract)

### Tess4J Installation
1. Download Tess4J from [Tess4J Releases](https://sourceforge.net/projects/tess4j/files/).
2. Add the `tess4j.jar` and its dependencies to your project's classpath.
3. Ensure the Tesseract native binaries are accessible (see Tess4J documentation).

### Building and Running
1. Clone or download this repository.
2. Add Tess4J and dependencies to your classpath.
3. Compile the Java file:
   ```sh
   javac -cp ".:path/to/tess4j.jar:path/to/dependencies/*" ImageToText.java
   ```
4. Run the application:
   ```sh
   java -cp ".:path/to/tess4j.jar:path/to/dependencies/*" ImageToText
   ```

## Usage
- Launch the application.
- Use the **Image to Text Conversion** button to drag and drop an image and extract text.
- View all previous conversions via the **Past Conversions** button.

## File Storage
- Extracted texts are saved in a local file (`conversions.txt`) for later viewing.

## Credits
- Developed by Jason He
- OCR powered by [Tesseract OCR](https://github.com/tesseract-ocr/tesseract) and [Tess4J](https://tess4j.sourceforge.net/)

## License
This project is for educational and personal use. 