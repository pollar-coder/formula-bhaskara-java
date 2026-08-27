//Lara Sthefany Feitosa de Miranda

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class FormulaBhaskara {
    private final JTextField campoA = new JTextField(12);
    private final JTextField campoB = new JTextField(12);
    private final JTextField campoC = new JTextField(12);
    private final JTextArea resultadoFinal = new JTextArea();
    private final JTextArea resultado = new JTextArea();
    private JFrame janelaCalculo;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormulaBhaskara().criarJanela());
    }

    private void criarJanela() {
        JFrame janela = new JFrame("F\u00f3rmula de Bhaskara");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(420, 380);
        janela.setLocationRelativeTo(null);
        janela.setResizable(false);

        JLabel titulo = new JLabel("Calculadora de Bhaskara", SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));

        JPanel campos = new JPanel(new GridLayout(3, 2, 8, 8));
        campos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        campos.add(new JLabel("Valor de A:"));
        campos.add(campoA);
        campos.add(new JLabel("Valor de B:"));
        campos.add(campoB);
        campos.add(new JLabel("Valor de C:"));
        campos.add(campoC);

        campoA.addActionListener(event -> campoB.requestFocusInWindow());
        campoB.addActionListener(event -> campoC.requestFocusInWindow());
        campoC.addActionListener(event -> calcular());

        JButton calcular = new JButton("Calcular");
        calcular.addActionListener(event -> calcular());

        JButton limpar = new JButton("Limpar");
        limpar.addActionListener(event -> limpar());

        JButton abrirCalculo = new JButton("calculo detalhado");
        abrirCalculo.addActionListener(event -> abrirJanelaCalculo());

       
        JPanel botoes = new JPanel();
        botoes.add(calcular);
        botoes.add(abrirCalculo);
        botoes.add(limpar);

        resultadoFinal.setEditable(false);
        resultadoFinal.setRows(3);
        resultadoFinal.setLineWrap(true);
        resultadoFinal.setWrapStyleWord(true);
        resultadoFinal.setText("O resultado final aparecerá aqui.");
        resultadoFinal.setBorder(BorderFactory.createTitledBorder("Resultado final"));

        JPanel formulario = new JPanel(new FlowLayout(FlowLayout.CENTER));
        formulario.add(campos);

        janela.add(titulo, BorderLayout.NORTH);
        janela.add(formulario, BorderLayout.CENTER);

        JPanel parteInferior = new JPanel(new BorderLayout());
        parteInferior.add(botoes, BorderLayout.NORTH);
        parteInferior.add(resultadoFinal, BorderLayout.CENTER);
        janela.add(parteInferior, BorderLayout.SOUTH);

        janela.setVisible(true);
    }

    private void abrirJanelaCalculo() {
        if (janelaCalculo == null) {
            janelaCalculo = new JFrame("Cálculo de Bhaskara");
            janelaCalculo.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            janelaCalculo.setSize(620, 560);
            janelaCalculo.setLocationRelativeTo(null);

            resultado.setEditable(false);
            resultado.setRows(6);
            resultado.setLineWrap(true);
            resultado.setWrapStyleWord(true);
            resultado.setText("Digite os valores de A, B e C e clique em Calcular.");
            resultado.setBorder(BorderFactory.createTitledBorder("Resultado:"));

            JScrollPane rolagem = new JScrollPane(resultado);
            rolagem.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            janelaCalculo.add(rolagem);
        }

        janelaCalculo.setVisible(true);
        janelaCalculo.toFront();
    }

    private void calcular() {
        try {
            abrirJanelaCalculo();

            double a = lerValor(campoA);
            double b = lerValor(campoB);
            double c = lerValor(campoC);

            if (a == 0) {
                resultadoFinal.setText("Aviso: o valor de A não pode ser zero.");
                mostrarAviso("O valor de A n\u00e3o pode ser zero.");
                return;
            }

            double delta = (b * b) - (4 * a * c);
            String calculoDelta = String.format(Locale.US,
                    "CÁLCULO DO DELTA\n\n"
                    + "Δ = b² - 4ac\n"
                    + "Δ = (%.2f)² - 4 × (%.2f) × (%.2f)\n"
                    + "Δ = %.2f - %.2f\n"
                    + "Δ = %.2f\n\n",
                    b, a, c, b * b, 4 * a * c, delta);

            if (delta < 0) {
                resultadoFinal.setText("Não existem raízes reais.\nDelta = "
                        + String.format(Locale.US, "%.2f", delta));
                resultado.setText(String.format(Locale.US,
                    "RESULTADO:\nNão existem raízes reais.\n\n"
                    + calculoDelta
                        + "FÓRMULA DE BHASKARA\n"
                        + "x = (-b ± √Δ) / 2a\n\n"
                        + "Como Δ = %.2f é menor que zero, não existe raiz quadrada real.\n\n"
                    + "Não há raízes reais para calcular.", delta));
            } else if (delta == 0) {
                double x = -b / (2 * a);
                resultadoFinal.setText(String.format(Locale.US,
                        "Uma raiz real: x = %.2f\nDelta = %.2f", x, delta));
                resultado.setText(String.format(Locale.US,
                    "RESULTADO:\nUma raiz real: x = %.2f\n\n"
                    + calculoDelta
                        + "FÓRMULA DE BHASKARA\n"
                        + "x = (-b ± √Δ) / 2a\n"
                        + "x = (-%.2f ± √%.2f) / (2 × %.2f)\n"
                        + "x = %.2f / %.2f\n\n",
                    x, b, delta, a, -b, 2 * a));
            } else {
                double raiz = Math.sqrt(delta);
                double x1 = (-b + raiz) / (2 * a);
                double x2 = (-b - raiz) / (2 * a);
                resultadoFinal.setText(String.format(Locale.US,
                    "Duas raízes reais:\nx1 = %.2f\n\nx2 = %.2f\nDelta = %.2f",
                    x1, x2, delta));
                resultado.setText(String.format(Locale.US,
                        "RESULTADO:\n\nDuas raízes reais:\nx1 = %.2f\nx2 = %.2f\n\n"
                        + calculoDelta
                        + "FÓRMULA DE BHASKARA\n\n"
                        + "\nx = (-b ± √Δ) / 2a\n"
                        + "√Δ = √%.2f = %.2f\n\n"
                        + "x1 = (-%.2f + %.2f) / (2 × %.2f)\n"
                        + "x1 = %.2f / %.2f\n"
                        + "x1 = %.2f\n\n"
                        + "x2 = (-%.2f - %.2f) / (2 × %.2f)\n"
                        + "x2 = %.2f / %.2f\n"
                        + "x2 = %.2f\n\n",
                        x1, x2, delta, delta, raiz, b, raiz, a, -b + raiz, 2 * a, x1,
                        b, raiz, a, -b - raiz, 2 * a, x2));
            }
        } catch (NumberFormatException exception) {
            resultadoFinal.setText("Aviso: preencha os três campos somente com números.");
            mostrarAviso("Preencha todos os campos somente com n\u00fameros.");
        }
    }

    private double lerValor(JTextField campo) throws NumberFormatException {
        String texto = campo.getText().trim().replace(',', '.');
        if (texto.isEmpty()) {
            throw new NumberFormatException();
        }
        double valor = Double.parseDouble(texto);
        if (!Double.isFinite(valor)) {
            throw new NumberFormatException();
        }
        return valor;
    }

    private void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    private void limpar() {
        campoA.setText("");
        campoB.setText("");
        campoC.setText("");
        resultadoFinal.setText("O resultado final.");
        resultado.setText("Digite os valores de A, B e C e clique em Calcular.");
        campoA.requestFocus();
    }
}
