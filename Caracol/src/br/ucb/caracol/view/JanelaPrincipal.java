package br.ucb.caracol.view;

import br.ucb.caracol.business.AnalisadorLexico;
import br.ucb.caracol.servicos.Servicos;
import br.ucb.caracol.servicos.Validacoes;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class JanelaPrincipal extends javax.swing.JFrame {

	public JanelaPrincipal() {
		initComponents();
		setResizable(false);
	}

	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		paineis = new javax.swing.JLayeredPane();
		painelLogo = new javax.swing.JPanel();
		l_imagem = new javax.swing.JLabel();
		painelCompilador = new javax.swing.JPanel();
		sp_feedBack = new javax.swing.JScrollPane();
		txt_feedBack = new javax.swing.JTextArea();
		b_compilar = new javax.swing.JButton();
		b_salvar = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		txt_codigo = new javax.swing.JTextPane();
		mBarra = new javax.swing.JMenuBar();
		menu = new javax.swing.JMenu();
		itemAbrir = new javax.swing.JMenuItem();
		separator = new javax.swing.JPopupMenu.Separator();
		itemSaida = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		l_imagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/ucb/caracol/imagens/CARACOL.png"))); // NOI18N

		javax.swing.GroupLayout painelLogoLayout = new javax.swing.GroupLayout(painelLogo);
		painelLogo.setLayout(painelLogoLayout);
		painelLogoLayout.setHorizontalGroup(
				painelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(painelLogoLayout.createSequentialGroup()
						.addGap(192, 192, 192)
						.addComponent(l_imagem)
						.addContainerGap(201, Short.MAX_VALUE))
				);
		painelLogoLayout.setVerticalGroup(
				painelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(painelLogoLayout.createSequentialGroup()
						.addGap(98, 98, 98)
						.addComponent(l_imagem)
						.addContainerGap(132, Short.MAX_VALUE))
				);

		painelLogo.setBounds(0, 0, 760, 630);
		paineis.add(painelLogo, javax.swing.JLayeredPane.DEFAULT_LAYER);

		painelCompilador.setVisible(false);

		txt_feedBack.setColumns(20);
		txt_feedBack.setLineWrap(true);
		txt_feedBack.setRows(5);
		sp_feedBack.setViewportView(txt_feedBack);

		b_compilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/ucb/caracol/imagens/compilar.png"))); // NOI18N
		b_compilar.setText("Compilar");
		b_compilar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_compilarActionPerformed(evt);
			}
		});

		b_salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/ucb/caracol/imagens/save.png"))); // NOI18N
		b_salvar.setText("Salvar Alterações");
		b_salvar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_salvarActionPerformed(evt);
			}
		});

		txt_codigo.setBorder(new NumeredBorder());
		jScrollPane1.setViewportView(txt_codigo);

		javax.swing.GroupLayout painelCompiladorLayout = new javax.swing.GroupLayout(painelCompilador);
		painelCompilador.setLayout(painelCompiladorLayout);
		painelCompiladorLayout.setHorizontalGroup(
				painelCompiladorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCompiladorLayout.createSequentialGroup()
						.addGap(43, 43, 43)
						.addGroup(painelCompiladorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
								.addComponent(sp_feedBack, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(painelCompiladorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(b_compilar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(b_salvar))
										.addContainerGap())
				);
		painelCompiladorLayout.setVerticalGroup(
				painelCompiladorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(painelCompiladorLayout.createSequentialGroup()
						.addGap(23, 23, 23)
						.addGroup(painelCompiladorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(painelCompiladorLayout.createSequentialGroup()
										.addComponent(b_compilar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(b_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(painelCompiladorLayout.createSequentialGroup()
												.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(sp_feedBack, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addContainerGap(75, Short.MAX_VALUE))
				);

		painelCompilador.setBounds(0, 0, 760, 650);
		paineis.add(painelCompilador, javax.swing.JLayeredPane.DEFAULT_LAYER);

		menu.setText("Inicio");

		itemAbrir.setText("Abrir Arquivo");
		itemAbrir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				itemAbrirActionPerformed(evt);
			}
		});
		menu.add(itemAbrir);
		menu.add(separator);

		itemSaida.setText("Sair");
		itemSaida.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				itemSaidaActionPerformed(evt);
			}
		});
		menu.add(itemSaida);

		mBarra.add(menu);

		setJMenuBar(mBarra);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(paineis, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
						.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(paineis, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
						.addContainerGap())
				);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void itemAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAbrirActionPerformed

		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("arquivos col", "col","COL");
		fc.setFileFilter(extensionFilter);
		fc.setMultiSelectionEnabled(false);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int res = fc.showOpenDialog(null);

		if (res == JFileChooser.APPROVE_OPTION) {

			File arq = fc.getSelectedFile();

			setPath(arq.getAbsolutePath());
			if (Validacoes.isArqValido(getPath())){
				try {
					txt_codigo.setText(Servicos.carregaArquivo(getPath()).trim());
				} catch (IOException ex) {
					Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
				}
				painelLogo.setVisible(false);
				painelCompilador.setVisible(true);
			}
			else{
				View.showMsgErro("Tipo de arquivo não é válido.");
			}
		} else {
			View.showMsg("Voce não selecionou nenhum arquivo.");
		}
	}//GEN-LAST:event_itemAbrirActionPerformed

	private void itemSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSaidaActionPerformed
		System.exit(0);
	}//GEN-LAST:event_itemSaidaActionPerformed

	private void b_compilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_compilarActionPerformed
		setTxt_feedBack("");
		AnalisadorLexico.analisadorLexico(getTxt_codigo().getText());
	}//GEN-LAST:event_b_compilarActionPerformed

	private void b_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_salvarActionPerformed
		try {
			Servicos.salvaArquivo(txt_codigo.getText());
		} catch (IOException ex) {
			Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
		}
	}//GEN-LAST:event_b_salvarActionPerformed

	public static String getPath() {
		return path;
	}

	public static void setPath(String path) {
		JanelaPrincipal.path = path;
	}

	private static String path;
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton b_compilar;
	private javax.swing.JButton b_salvar;
	private javax.swing.JMenuItem itemAbrir;
	private javax.swing.JMenuItem itemSaida;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel l_imagem;
	private javax.swing.JMenuBar mBarra;
	private javax.swing.JMenu menu;
	private javax.swing.JLayeredPane paineis;
	private javax.swing.JPanel painelCompilador;
	private javax.swing.JPanel painelLogo;
	private javax.swing.JPopupMenu.Separator separator;
	private javax.swing.JScrollPane sp_feedBack;
	private static javax.swing.JTextPane txt_codigo;
	private static javax.swing.JTextArea txt_feedBack;
	// End of variables declaration//GEN-END:variables

	public static JTextPane getTxt_codigo() {
		return txt_codigo;
	}

	public static void setTxt_codigo(String str) {
		JanelaPrincipal.txt_codigo.setText(str); 
	}

	public static JTextArea getTxt_feedBack() {
		return txt_feedBack;
	}

	public static void setTxt_feedBack(String str) {
		JanelaPrincipal.txt_feedBack.setText(str);
	}
}
