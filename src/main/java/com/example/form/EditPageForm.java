package com.example.form;

public class EditPageForm {
	
	private String categoryName;

	private Integer pageNumber;
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	@Override
	public String toString() {
		return "EditPageForm [categoryName=" + categoryName + ", pageNumber=" + pageNumber + "]";
	}

}
