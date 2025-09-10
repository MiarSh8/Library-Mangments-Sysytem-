
package FxlLibraryMangmentsSysytem;

public class Book {
    private String image;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private int copyCount;
    private int pageCount;
    private String publishDate;
    private String language;

    // Constructor
    public Book(String image, String title, String author, String category, String isbn, int copyCount, int pageCount, String publishDate, String language) {
        this.image = image;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.copyCount = copyCount;
        this.pageCount = pageCount;
        this.publishDate = publishDate;
        this.language = language;
    }

    // Getters and Setters
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getCopyCount() {
        return copyCount;
    }

    public void setCopyCount(int copyCount) {
        this.copyCount = copyCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
