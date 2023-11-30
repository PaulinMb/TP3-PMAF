namespace WinFormapp
{
    public partial class Form1 : Form
    {
        private ApiCall api;
        public Form1()
        {
            api = new ApiCall();
            InitializeComponent();
        }

        private async void button1_Click(object sender, EventArgs e)
        {
            string message = await api.callApi();
            textBox1.Text = message;

        }

        
    }
}
