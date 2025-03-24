

class Book {
    String title
    String author
    Date publishedDate

    static constraints = {
        title blank: false
        author blank: false
        publishedDate nullable: true
    }
}