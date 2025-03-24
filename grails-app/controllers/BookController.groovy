

class BookController {
    static responseFormats = ['json']
    static allowedMethods = [index: "GET", show: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        respond Book.list()
    }

    def show(Long id) {
        def book = Book.get(id)
        if (book) {
            respond book
        } else {
            render status: 404
        }
    }

    def save() {
        def book = new Book(request.JSON)
        if (book.validate() && book.save()) {
            respond book, [status: 201]
        } else {
            respond book.errors, [status: 422]
        }
    }

    def update(Long id) {
        def book = Book.get(id)
        if (!book) {
            render status: 404
            return
        }

        book.properties = request.JSON
        if (book.validate() && book.save()) {
            respond book
        } else {
            respond book.errors, [status: 422]
        }
    }

    def delete(Long id) {
        def book = Book.get(id)
        if (book) {
            book.delete()
            render status: 204
        } else {
            render status: 404
        }
    }
}
