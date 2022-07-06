package Notas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Beatriz C
 *
 */

public class Notas {
	private int id;
	private String nome;
	private int sala;
	private double media;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getSala() {
		return sala;
	}
	public void setSala(int sala) {
		this.sala = sala;
	}
	public double getMedia() {
		return media;
	}
	public void setMedia(double media) {
		this.media = media;
	}
	public boolean cadastrarAluno(String nome, int sala, double media) {
		igualaID();
		int id = this.id;
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			String sql = "insert into alunos set id=?, nome=?, sala=?, media=?;";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, nome);
			ps.setInt(3, sala); 
			ps.setDouble(4, media); 
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0) {
				System.out.println("Não foi feito o cadastro!!");
				return false;
			}
			System.out.println("Cadastro realizado!");
			igualaID();
			return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao cadastrar produto: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	
	
	public boolean consultarAluno(int id) {
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			String sql = "select * from alunos where id=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, id); 
			ResultSet rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) {
				return false;
			} else {
				while (rs.next()) {
					this.id = rs.getInt("id");
					this.nome = rs.getString("nome");
					this.sala = rs.getInt("sala");
					this.media = rs.getDouble("media");
				}
				return true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar a media: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	
	public boolean atualizarMedia(int id, int sala, double media, String nome) {
		if (!consultarAluno(id))
			return false;
		else {
			// Define a conexão
			Connection conexao = null;
			try {
				// Define a conexão
				conexao = Conexao.conectaBanco();
				// Define a consulta
				String sql = "update alunos set nome=?, sala=?, media=? where aluno.id=?";
				// Prepara a consulta
				PreparedStatement ps = conexao.prepareStatement(sql);
				// Define os parâmetros da atualização
				ps.setString(1, nome);
				ps.setInt(2, sala);
				ps.setDouble(3, media);
				ps.setInt(4, id);
				int totalRegistrosAfetados = ps.executeUpdate();
				if (totalRegistrosAfetados == 0)
					System.out.println("Não foi feita a atualização!");
				else
					System.out.println("Atualização realizada!");
				return true;
			} catch (SQLException erro) {
				System.out.println("Erro ao atualizar a media: " + erro.toString());
				return false;
			} finally {
				Conexao.fechaConexao(conexao);
			}
		}
	}
	
	public boolean deletarProduto(int id) {
		// Define a conexão
		igualaID();
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			String sql = "delete from alunos where id=?;";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, id);
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0) {
				System.out.println("Não foi feita remoção!");
				return false;
			}
			System.out.println("Remoção realizada!");
			igualaID();
			return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao remover aluno: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	
	public void igualaID() {
		Connection conexao = null;
		conexao = Conexao.conectaBanco();
		try {
			String sql = "select max(id) from alunos";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				this.id= rs.getInt("max(id)") + 1;
			}
		}catch (SQLException erro) {
			System.out.println("Erro ao igualar ID: " + erro.toString());
		}	finally{
			Conexao.fechaConexao(conexao);
		}
	}
	
	public boolean checaTabela() {
		int check=0;
		Connection conexao = null;
		conexao = Conexao.conectaBanco();
		try {
			String sql = "select max(id) from alunos";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				check = rs.getInt("max(id)");
			}
		if(check!=0) {
			return true;
		}
		else {
			return false;
		}
		}catch (SQLException erro) {
			System.out.println("Erro ao checar alunos: " + erro.toString());
			return false;
		}	finally{
			Conexao.fechaConexao(conexao);
		}
	}
}

