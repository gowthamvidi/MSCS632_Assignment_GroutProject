using System.Text.Json;
using System.IO;


namespace BookCatalogApp

{
    public partial class Form1 : Form
    {

        private List<Book> bookCatalog = new List<Book>();

        public Form1()
        {

            InitializeComponent();
            LoadBooksFromJson();
        }

        private void label3_Click(object sender, EventArgs e)
        {

        }

        private void label4_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            // Validate and parse year
            if (!int.TryParse(textBox4.Text, out int year))
            {
                MessageBox.Show("Please enter a valid year.");
                return;
            }

            // Create a new book object
            Book newBook = new Book
            {
                Title = textBox1.Text,
                Author = textBox2.Text,
                Genre = textBox3.Text,
                Year = year
            };

            // Add to catalog
            bookCatalog.Add(newBook);

            // Save to JSON file
            SaveBooksToJson();

            // Optional: Show confirmation
            MessageBox.Show("Book added successfully!");

            // Optional: Clear inputs
            textBox1.Text = "";
            textBox2.Text = "";
            textBox3.Text = "";
            textBox4.Text = "";
        }


        private string GetProjectJsonPath()
        {
            // Navigate from bin\Debug\net6.0-windows to project root
            string baseDir = AppDomain.CurrentDomain.BaseDirectory;
            string projectDir = Path.GetFullPath(Path.Combine(baseDir, @"..\..\..\"));
            return Path.Combine(projectDir, "books.json");
        }

        private void SaveBooksToJson()
        {
            string jsonPath = GetProjectJsonPath();
            string json = JsonSerializer.Serialize(bookCatalog, new JsonSerializerOptions { WriteIndented = true });
            File.WriteAllText(jsonPath, json);
        }

        private void LoadBooksFromJson()
        {
            string jsonPath = GetProjectJsonPath();
            //MessageBox.Show($"Trying to load from: {jsonPath}");

            if (File.Exists(jsonPath))
            {
                try
                {
                    string json = File.ReadAllText(jsonPath);
                    bookCatalog = JsonSerializer.Deserialize<List<Book>>(json) ?? new List<Book>();
                    RefreshGrid();
                    //MessageBox.Show($"Loaded {bookCatalog.Count} books.");
                }
                catch (Exception ex)
                {
                    MessageBox.Show($"Error loading books: {ex.Message}");
                }
            }
            else
            {
                MessageBox.Show("No saved books found.");
            }
        }




        private void RefreshGrid()
        {
            dgvBooks.DataSource = null;
            dgvBooks.DataSource = bookCatalog;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            string jsonPath = GetProjectJsonPath(); // Use the correct path

            if (File.Exists(jsonPath))
            {
                string json = File.ReadAllText(jsonPath);
                bookCatalog = JsonSerializer.Deserialize<List<Book>>(json) ?? new List<Book>();
                RefreshGrid();
                MessageBox.Show("Books loaded successfully!");
            }
            else
            {
                MessageBox.Show("No saved books found.");
            }
        }


        private void textBox3_TextChanged(object sender, EventArgs e)
        {

        }


        private void btnRemove_Click(object sender, EventArgs e)
        {
            if (dgvBooks.SelectedRows.Count > 0)
            {
                var selectedBook = (Book)dgvBooks.SelectedRows[0].DataBoundItem;
                bookCatalog.Remove(selectedBook);
                SaveBooksToJson();
                RefreshGrid();
            }
        }


        private void button3_Click(object sender, EventArgs e)
        {
            string query = txtSearch.Text.ToLower();

            var results = bookCatalog.Where(b =>
                b.Title.ToLower().Contains(query) ||
                b.Author.ToLower().Contains(query) ||
                b.Genre.ToLower().Contains(query)).ToList();

            dgvBooks.DataSource = null;
            dgvBooks.DataSource = results;
        }

        private void txtSearch_TextChanged(object sender, EventArgs e)
        {

        }

        private void btnReport_Click(object sender, EventArgs e)
        {
            string reportType = cmbReportType.SelectedItem?.ToString();

            if (reportType == "Genre")
            {
                var report = bookCatalog
                    .GroupBy(b => b.Genre.Trim().ToLower())
                    .Select(g => new { Genre = g.Key, Count = g.Count() })
                    .ToList();

                dgvBooks.DataSource = report;
            }
            else if (reportType == "Author")
            {
                var report = bookCatalog
                    .GroupBy(b => b.Author.Trim().ToLower())
                    .Select(g => new { Author = g.Key, Count = g.Count() })
                    .ToList();

                dgvBooks.DataSource = report;
            }
        }

        private void cmbReportType_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void btnRemove_Click_1(object sender, EventArgs e)
        {
            if (dgvBooks.SelectedRows.Count > 0)
            {
                var selectedBook = (Book)dgvBooks.SelectedRows[0].DataBoundItem;

                var confirm = MessageBox.Show($"Remove '{selectedBook.Title}'?", "Confirm", MessageBoxButtons.YesNo);
                if (confirm == DialogResult.Yes)
                {
                    bookCatalog.Remove(selectedBook);
                    SaveBooksToJson();
                    RefreshGrid();
                }
            }
            else
            {
                MessageBox.Show("Please select a book to remove.");
            }
        }
    }


}
