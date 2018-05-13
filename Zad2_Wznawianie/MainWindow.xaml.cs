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

namespace Zad2_Wznawianie
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private readonly Thread[] _threads;
        private readonly TextBox textBox;

        public MainWindow()
        {
            InitializeComponent();

            textBox = (TextBox)FindName("OutputBox");
            var startButtonsPanel = (StackPanel)FindName("StartButtons");
            var stopButtonsPanel = (StackPanel)FindName("StopButtons");

            if (textBox == null || startButtonsPanel == null || stopButtonsPanel == null)
                throw new NullReferenceException();

            _threads = new Thread[10];
            for (int i = 1; i <= 10; i++)
            {
                int nr = i % 10;

                //Create buttons
                var bstart = new Button {Content = $"Start {nr} thread", Margin = new Thickness(5)};
                var bstop = new Button {Content = $"Stop {nr} thread", Margin = new Thickness(5) };
                
                //Create Threads
                _threads[nr] = new Thread(() =>
                {
                    for (char c = 'A'; c <= 'Z'; c++)
                    {
                        SendMessage($@"{c}{nr}");
                        Thread.Sleep(1000);
                    }

                    textBox.Dispatcher.BeginInvoke(new Action(() => { bstop.IsEnabled = false; }));
                });

                //Create UI events handler
                bstart.Click += (sender, args) =>
                {
                    if (_threads[nr].IsAlive)
                    {
                        SendMessage($"Resume thread nr {nr}");
                        bstop.IsEnabled = true;
                        bstart.IsEnabled = false;
                        _threads[nr].Resume();
                    }
                    else
                    {
                        SendMessage($"Thread nr {nr} started");
                        _threads[nr].Start();
                        bstart.IsEnabled = false;
                    }
                };

                bstop.Click += (sender, args) =>
                {
                    if (_threads[nr].IsAlive)
                    {
                        _threads[nr].Suspend();
                        bstart.Content = $"Resume {nr} thread";
                        bstart.IsEnabled = true;
                        bstop.IsEnabled = false;
                        SendMessage($"Thread nr {nr} suspended");
                    }
                    else
                    {
                        SendMessage($"Thread nr {nr} can't be stopped because It isn't working");
                    }
                };

                startButtonsPanel.Children.Add(bstart);
                stopButtonsPanel.Children.Add(bstop);
            }
        }

        private void SendMessage(String message)
        {
            textBox.Dispatcher.BeginInvoke(new Action(() =>
            {
                textBox.AppendText(message + "\n");
                textBox.ScrollToEnd();
            }));
        }

        protected override void OnClosed(EventArgs e)
        {
            base.OnClosed(e);

            foreach (var thread in _threads)
            {
                thread.Abort();
            }
        }
    }
}
