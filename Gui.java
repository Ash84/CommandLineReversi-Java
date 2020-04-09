import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;

public class Gui extends JFrame{

  private static final long serialVersionUID = 1L;

  public Gui() {
        JPanel container = new JPanel();
        this.setTitle("Border Layout");
        this.setSize(580, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLayout(new BorderLayout());
        JButton button1 = new JButton("Jouer Contre humain"); 
        JButton button2 = new JButton("Jouer Contre machine"); 
        JButton button3 = new JButton("PCU vs PCU");
        JLabel label = new JLabel("Choix type Partie");
        label.setHorizontalAlignment(JLabel.CENTER);

        container.add(button1, BorderLayout.EAST);
        container.add(button2, BorderLayout.WEST);
        container.add(button3, BorderLayout.CENTER);
        container.add(label, BorderLayout.SOUTH);
        
        button1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            game.genereretjouerlapartiecontrehumain();
          }});

        button1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            SwingUtilities.getWindowAncestor(container).dispose();
          }});

        button2.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            game.genereretjouerlapartiecontremachine();
          }});

        button2.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            SwingUtilities.getWindowAncestor(container).dispose();          
          }});

        button3.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            game.genereretjouerPCUVSPCU();
          }});

        button3.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            SwingUtilities.getWindowAncestor(container).dispose();
          }});

        this.setContentPane(container);
        this.setVisible(true);
    }


    public static void main(String args[]){
      Gui layout = new Gui();
    }
}


