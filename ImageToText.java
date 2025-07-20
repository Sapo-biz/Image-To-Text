import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.DataFlavor;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.io.*;

public class ImageToText {
    private JFrame frame;
    private JPanel homePanel, conversionPanel, pastPanel;
    private CardLayout cardLayout;
    private JTextArea ocrResultArea;
    private JTextArea pastText;
    private static final String CONVERSIONS_FILE = "conversions.txt";

    public ImageToText() {
        frame = new JFrame("Image To Text Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        cardLayout = new CardLayout();
        frame.setLayout(cardLayout);

        // Home Menu
        homePanel = new JPanel();
        homePanel.setLayout(new GridBagLayout());
        JButton btnConvert = new JButton("Image to Text Conversion");
        JButton btnPast = new JButton("Past Conversions");
        btnConvert.addActionListener(e -> cardLayout.show(frame.getContentPane(), "convert"));
        btnPast.addActionListener(e -> cardLayout.show(frame.getContentPane(), "past"));
        homePanel.add(btnConvert);
        homePanel.add(btnPast);

        // Conversion Panel
        conversionPanel = new JPanel(new BorderLayout());
        JPanel dragPanel = new JPanel(new BorderLayout());
        JLabel dragLabel = new JLabel("Drag an image here", SwingConstants.CENTER);
        dragLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        dragPanel.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
        dragPanel.add(dragLabel, BorderLayout.CENTER);
        dragPanel.setPreferredSize(new Dimension(400, 200));

        // Drag-and-drop support
        dragPanel.setTransferHandler(new TransferHandler() {
            public boolean canImport(TransferHandler.TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }
            public boolean importData(TransferHandler.TransferSupport support) {
                if (!canImport(support)) return false;
                try {
                    java.util.List files = (java.util.List) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if (files.size() > 0) {
                        java.io.File file = (java.io.File) files.get(0);
                        // Placeholder: Show file path and call OCR stub
                        ocrResultArea.setText("Processing: " + file.getName() + "\n(Performing OCR...)");
                        // Call OCR method (to be implemented)
                        String text = performOCR(file);
                        ocrResultArea.setText(text);
                    }
                } catch (Exception ex) {
                    ocrResultArea.setText("Error: " + ex.getMessage());
                }
                return true;
            }
        });

        ocrResultArea = new JTextArea();
        ocrResultArea.setEditable(false);
        ocrResultArea.setLineWrap(true);
        ocrResultArea.setWrapStyleWord(true);
        JScrollPane ocrScroll = new JScrollPane(ocrResultArea);

        JButton backBtn1 = new JButton("Back");
        backBtn1.addActionListener(e -> cardLayout.show(frame.getContentPane(), "home"));

        conversionPanel.add(dragPanel, BorderLayout.NORTH);
        conversionPanel.add(ocrScroll, BorderLayout.CENTER);
        conversionPanel.add(backBtn1, BorderLayout.SOUTH);

        // Past Conversions Panel (to be implemented)
        pastPanel = new JPanel();
        pastPanel.setLayout(new BorderLayout());
        pastText = new JTextArea();
        pastText.setEditable(false);
        pastPanel.add(new JScrollPane(pastText), BorderLayout.CENTER);
        JButton backBtn2 = new JButton("Back");
        backBtn2.addActionListener(e -> cardLayout.show(frame.getContentPane(), "home"));
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadPastConversions());
        JPanel pastBtnPanel = new JPanel();
        pastBtnPanel.add(backBtn2);
        pastBtnPanel.add(refreshBtn);
        pastPanel.add(pastBtnPanel, BorderLayout.SOUTH);
        // Load conversions on panel show
        pastPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent e) {
                loadPastConversions();
            }
        });

        frame.add(homePanel, "home");
        frame.add(conversionPanel, "convert");
        frame.add(pastPanel, "past");
        cardLayout.show(frame.getContentPane(), "home");
        frame.setVisible(true);
    }

    // OCR method using Tess4J
    private String performOCR(java.io.File imageFile) {
        try {
            Tesseract tesseract = new Tesseract();
            // Optionally set the tessdata path if not in PATH
            // tesseract.setDatapath("/usr/local/share/tessdata");
            String result = tesseract.doOCR(imageFile);
            saveConversion(result);
            return result;
        } catch (TesseractException e) {
            return "OCR Error: " + e.getMessage();
        }
    }

    // Save extracted text to file
    private void saveConversion(String text) {
        try (FileWriter fw = new FileWriter(CONVERSIONS_FILE, true); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("--- Conversion ---\n");
            bw.write(text.trim() + "\n\n");
        } catch (IOException e) {
            // Optionally handle error
        }
    }

    // Load past conversions from file
    private void loadPastConversions() {
        try (FileReader fr = new FileReader(CONVERSIONS_FILE); BufferedReader br = new BufferedReader(fr)) {
            pastText.setText("");
            String line;
            while ((line = br.readLine()) != null) {
                pastText.append(line + "\n");
            }
        } catch (IOException e) {
            pastText.setText("No past conversions found.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImageToText::new);
    }
}
