package models.CSVRow;

import models.Course;

public class RowCourse extends Course {
	private int version;
	private Course course;
	
	public RowCourse(int version, Course course) {
		//super(course);
		this.version = version;
		this.course=course;
	}

	@Override
	public String getRowCsv() {
		String row = "";
		switch (version) {
		case 1:
			row = getRowCsv1();
			break;

		default:
			row = course.getRowCsv();
			break;
		}
		return row;
	}

	@Override
	public String getEnTeteCsv() {
		String enTete = "";
		switch (version) {
		case 1:
			enTete = getEnTeteCsv1();
			break;

		default:
			enTete = course.getEnTeteCsv();
			break;
		}
		return enTete;
	}
	
	
	private String getEnTeteCsv1() {//TODO Save version
		return course.getEnTeteCsv();
	}
	private String getRowCsv1() {//TODO
		return course.getRowCsv();
	}

}
