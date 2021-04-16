package br.com.bbl.consolultra.service;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import br.com.bbl.consolultra.model.Answer;
import br.com.bbl.consolultra.model.AnswerCard;
import br.com.bbl.consolultra.model.AnswerSelected;
import br.com.bbl.consolultra.model.Evaluation;
import br.com.bbl.consolultra.model.Happening;
import br.com.bbl.consolultra.model.Question;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Excel {

	public void create(Evaluation evaluation, HttpServletResponse response) {
		try {
			// Configura a planilha
			WorkbookSettings settings = new WorkbookSettings();
			settings.setLocale(new Locale("pt", "BR"));

			// Cria a planilha com uma aba chamada "Plan"
			WritableWorkbook book = Workbook.createWorkbook(response.getOutputStream(), settings);
			WritableSheet sheet = book.createSheet("Plan", 0);
			// response.getOutputStream()
			
			// Escreve o conteúdo na aba
			writeDataSheet(evaluation, sheet);

			// Escreve o conteúdo da aba na planilha e fecha a planilha
			book.write();
			book.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (WriteException e2) {
			e2.printStackTrace();
		}
	}

	private void writeDataSheet(Evaluation evaluation, WritableSheet sheet) throws WriteException {
		
		// Formata para o cabeçalho
		WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
		WritableCellFormat format = new WritableCellFormat(font);
		format.setWrap(false);
		
		// Escreve o cabeçalho
		sheet.addCell(new Label(0,1,"Avaliação",format));
		sheet.addCell(new Label(1,1,"Status",format));
		sheet.addCell(new Label(2,1,"Especialidade",format));
		sheet.addCell(new Label(3,1,"Experência",format));

		int y = 4;
		for (Happening happening : evaluation.getHappenings()) {
			// Insere o nome do caso na 
			sheet.addCell(new Label(y,0,happening.getName(),format));
			
			// Insere as respostas do caso
			for (Question question : happening.getQuestions()) {
				for (Answer answer : question.getAnswers()) {
					sheet.addCell(new Label(y,1,answer.getDescription(),format));
					y++;
				}
			}
		}
		
		// Formata para o conteúdo
		font = new WritableFont(WritableFont.ARIAL, 10);
		format = new WritableCellFormat(font);
		format.setWrap(false);
		
		// Escreve o conteúdo
		int i = 2;
		for (AnswerCard answerCard : evaluation.getAnswerCards()) {
			
			String ti = evaluation.getTitle();
			sheet.addCell(new Label(0, i, ti, format));

			String st = answerCard.getState().name();
			sheet.addCell(new Label(1, i, st, format));

			String sp = (answerCard.getParticipant().getSpecialty() != null ? answerCard.getParticipant().getSpecialty() : "");
			sheet.addCell(new Label(2, i, sp, format));
			
			Integer ex = (answerCard.getParticipant().getExperience() != null ? answerCard.getParticipant().getExperience() : 0);
			sheet.addCell(new Number(3, i, ex, format));
			
			y = 4;
			for (Happening happening : evaluation.getHappenings()) {
				for (Question question : happening.getQuestions()) {
					for (Answer answer : question.getAnswers()) {

						for (AnswerSelected answerSelected : answerCard.getAnswerSelecteds()) {
							if (answer.equals(answerSelected.getAnswer()) && answer.getCorrect())
								sheet.addCell(new Label(y,i,"C",format));
							else if(answer.equals(answerSelected.getAnswer()) && !answer.getCorrect())
								sheet.addCell(new Label(y,i,"E",format));
						}
						y++;
					}
				}
			}
			i++;
		}
		
		
	}
}
