package Notas;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class View{
	public static void main(String[] args) {
		//Define a janela
		Notas media = new Notas();
		media.igualaID();
		JFrame janela = new JFrame("Registrar Média"); // Janela Normal
		janela.setResizable(false); // A janela não poderá ter o tamanho ajustado
		janela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		janela.setSize(600, 600); // Define tamanho da janela
		//Define o layout da janel
		Container caixa = janela.getContentPane();
		caixa.setLayout(null);
		//Define os labels dos campos
		JLabel labelSala = new JLabel("Sala: ");
		JLabel labelMedia = new JLabel("Media: ");
		JLabel labelNome = new JLabel("Nome: ");
		JLabel labelID = new JLabel("ID: ");
		//Posiciona os labels na janela
		labelSala.setBounds(50, 40, 100, 20); // coluna, linha, largura, tamanho
		labelMedia.setBounds(50, 80, 150, 20); // coluna, linha, largura, tamanho
		labelNome.setBounds(50, 120, 100, 20); // coluna, linha, largura, tamanho
		labelID.setBounds(50, 150, 100, 40);
		//Define os input box
		JTextField jTextSala = new JTextField();
		JTextField jTextMedia = new JTextField();
		JTextField jTextNome = new JTextField();
		JTextField jTextID = new JTextField();
		//Define se os campos estão habilitados ou não no início
		jTextSala.setEnabled(true);
		jTextMedia.setEnabled(true);
		jTextNome.setEnabled(true);
		jTextID.setEnabled(false);
		//Posiciona os input box
		jTextSala.setBounds(180, 40, 50, 20);
		jTextMedia.setBounds(180, 80, 50, 20);
		jTextNome.setBounds(180, 120, 150, 20);
		jTextID.setBounds(180, 160, 150, 20);
		//Adiciona os rótulos e os input box na janela
		janela.add(labelSala);
		janela.add(labelMedia);
		janela.add(labelNome);
		janela.add(labelID);
		janela.add(jTextSala);
		janela.add(jTextMedia);
		janela.add(jTextNome);
		janela.add(jTextID);
		//Define botões e a localização deles na janela
		JButton botaoConsultar = new JButton("Consultar");
		botaoConsultar.setBounds(360, 160, 100, 20);
		botaoConsultar.setEnabled(false);
		janela.add(botaoConsultar);
		JButton botaoRemocao = new JButton("Remover");
		botaoRemocao.setBounds(270, 200, 100, 20);
		botaoRemocao.setEnabled(false);
		janela.add(botaoRemocao);
		JButton botaoGravar = new JButton("Gravar");
		botaoGravar.setBounds(50, 200, 100, 20);
		botaoGravar.setEnabled(true);
		janela.add(botaoGravar);
		JButton botaoAtualizar = new JButton("Atualizar");
		botaoAtualizar.setBounds(380, 200, 100, 20);
		botaoAtualizar.setEnabled(false);
		janela.add(botaoAtualizar);
		JButton botaoLimpar = new JButton("Limpar");
		botaoLimpar.setBounds(160, 200, 100, 20);
		botaoLimpar.setEnabled(true);
		janela.add(botaoLimpar);
		if(media.checaTabela()) {
			botaoAtualizar.setEnabled(true);
			botaoRemocao.setEnabled(true);
			botaoConsultar.setEnabled(true);
			jTextID.setEnabled(true);
		}
		//Define objeto conta para pesquisar no banco de dados
		//Define ações dos botões
		
		botaoConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(jTextID.getText());
					String nome;
					int sala=0;
					double media0=0;
					if (!media.consultarAluno(id))
						nome = "";
					else {
						nome = media.getNome();
						sala = media.getSala();
						media0 = media.getMedia();
						id = media.getId();
					}
					jTextNome.setText(nome);
					jTextSala.setText(String.valueOf(sala));
					jTextMedia.setText(String.valueOf(media0));
					jTextID.setText(String.valueOf(id));
					jTextID.setEnabled(false);
					jTextNome.setEnabled(false);
					botaoConsultar.setEnabled(false);
					jTextSala.setEnabled(false);
					jTextMedia.setEnabled(false);
					jTextNome.requestFocus();
					jTextID.requestFocus();
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(janela,
							"Preencha os campos corretamente!!");
				}
			}
		});
		
		botaoGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sala = Integer.parseInt(jTextSala.getText());
				String nome = jTextNome.getText().trim(); // Retira os espaços em branco
				double media0 = Double.parseDouble(jTextMedia.getText());
				if (nome.length()==0) {
					JOptionPane.showMessageDialog(janela, "Preencha o campo nome!");
					jTextNome.requestFocus();
				}
				else if(media0 == 0) {
							JOptionPane.showMessageDialog(janela, "Preencha o campo media!");
							jTextMedia.requestFocus();
				}
				if(!media.cadastrarAluno(nome, sala, media0)) {
					JOptionPane.showMessageDialog(janela, "Erro no Cadastro!");
				}
				else {
					JOptionPane.showMessageDialog(janela, "Cadastro do aluno realizado com sucesso!");
					botaoLimpar.setEnabled(true);
					botaoAtualizar.setEnabled(true);
					botaoRemocao.setEnabled(true);
					botaoConsultar.setEnabled(true);
				}
			}
		});
		
		botaoAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(jTextID.getText());
				int sala = Integer.parseInt(jTextSala.getText());
				String nome = jTextNome.getText().trim(); // Retira os espaços em branco
				double media0 = Double.parseDouble(jTextMedia.getText());
				if (nome.length()==0) {
					JOptionPane.showMessageDialog(janela, "Preencha o campo nome!");
					jTextNome.requestFocus();
				}
				else if(media0 == 0) {
							JOptionPane.showMessageDialog(janela, "Preencha o campo media!");
							jTextMedia.requestFocus();
				}
				if(!media.atualizarMedia(id, sala, media0, nome)) {
					JOptionPane.showMessageDialog(janela, "Erro na Atualização!");
				}
				else {
					JOptionPane.showMessageDialog(janela, "Atualização da media realizada com sucesso!");
				}
			}
		});
		
		botaoRemocao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(jTextID.getText());
				if(!media.consultarAluno(id)) {
					JOptionPane.showMessageDialog(janela, "Aluno não existe ou não encontrado.");
				}
				else {
					if(media.deletarProduto(id));
					JOptionPane.showMessageDialog(janela, "Aluno removido!");
				}
				if(media.checaTabela()) {
					botaoAtualizar.setEnabled(true);
					botaoRemocao.setEnabled(true);
					botaoConsultar.setEnabled(true);
					jTextID.setEnabled(true);
				}
				else {
					botaoAtualizar.setEnabled(false);
					botaoRemocao.setEnabled(false);
					botaoConsultar.setEnabled(false);
					jTextID.setEnabled(false);
				}
		}
			
		});
		
		botaoLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextSala.setText(""); // Limpar campo
				jTextMedia.setText(""); // Limpar campo
				jTextNome.setText(""); // Limpar campo
				jTextID.setText("");
				jTextSala.setEnabled(true);
				jTextMedia.setEnabled(true);
				jTextNome.setEnabled(true);
				jTextID.setEnabled(true);
				botaoConsultar.setEnabled(true);
				botaoGravar.setEnabled(true);
			}
		});
		janela.setVisible(true); // Exibe a janela
	}
}