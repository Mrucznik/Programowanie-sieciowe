using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Zad5_Async
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private readonly TextBox _textBox;

        public MainWindow()
        {
            InitializeComponent();

            _textBox = (TextBox)FindName("OutputBox");
            var startButtonsPanel = (StackPanel)FindName("StartButtons");
            var stopButtonsPanel = (StackPanel)FindName("StopButtons");

            if (_textBox == null || startButtonsPanel == null || stopButtonsPanel == null)
                throw new NullReferenceException();
            
            for (int i = 1; i <= 10; i++)
            {
                int nr = i % 10;

                //Create buttons
                var bstart = new Button { Content = $"Start {nr} Task", Margin = new Thickness(5) };
                var bstop = new Button { Content = $"Stop {nr} Task", Margin = new Thickness(5), IsEnabled = false};

                //Create Tasks
                var tokenSource = new CancellationTokenSource();
                CancellationToken ct = tokenSource.Token;
                var task = new Task(() =>
                {
                    for (char c = 'A'; c <= 'Z'; c++)
                    {
                        SendMessage($@"{c}{nr}");
                        Thread.Sleep(1000);
                        if (ct.IsCancellationRequested)
                        {
                            ct.ThrowIfCancellationRequested();
                        }
                    }

                    _textBox.Dispatcher.BeginInvoke(new Action(() => { bstop.IsEnabled = false; }));
                }, tokenSource.Token);

                //Create UI events handler
                bstart.Click += (sender, args) =>
                {
                    task.Start();
                    bstart.IsEnabled = false;
                    bstop.IsEnabled = true;
                    SendMessage($"Task nr {nr} started");
                };

                bstop.Click += (sender, args) =>
                {
                    tokenSource.Cancel();
                    bstop.IsEnabled = false;
                    SendMessage($"Task nr {nr} canceled");
                };

                startButtonsPanel.Children.Add(bstart);
                stopButtonsPanel.Children.Add(bstop);
            }
        }

        private void SendMessage(String message)
        {
            _textBox.Dispatcher.BeginInvoke(new Action(() =>
            {
                _textBox.AppendText(message + "\n");
                _textBox.ScrollToEnd();
            }));
        }
    }
}
