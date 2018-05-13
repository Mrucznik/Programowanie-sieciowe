using System;
using System.Threading;
using System.Windows;
using System.Windows.Controls;

namespace Zad3_Synchronizacja
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private readonly Thread[] _threads;
        private readonly TextBox _textBox;
        private readonly object locker = new object();

        public MainWindow()
        {
            InitializeComponent();

            _textBox = (TextBox)FindName("OutputBox");
            var startButtonsPanel = (StackPanel)FindName("StartButtons");
            var stopButtonsPanel = (StackPanel)FindName("StopButtons");

            if (_textBox == null || startButtonsPanel == null || stopButtonsPanel == null)
                throw new NullReferenceException();

            _threads = new Thread[10];
            for (int i = 1; i <= 10; i++)
            {
                int nr = i % 10;

                //Create buttons
                var bstart = new Button { Content = $"Resume {nr} thread", Margin = new Thickness(5), IsEnabled = false};
                var bstop = new Button { Content = $"Stop {nr} thread", Margin = new Thickness(5) };

                //Create Threads
                var waitHandle = new ManualResetEvent(initialState: true);
                _threads[nr] = new Thread(() =>
                {
                    for (char c = 'A'; c <= 'Z'; c++)
                    {
                        lock (this)
                        {
                            SendMessage($@"{c}{nr}");
                            Thread.Sleep(1000);
                        }
                        waitHandle.WaitOne();
                    }

                    _textBox.Dispatcher.BeginInvoke(new Action(() => { bstop.IsEnabled = false; }));
                });
                _threads[nr].Start();

                //Create UI events handler
                bstart.Click += (sender, args) =>
                {
                    SendMessage($"Resume thread nr {nr}");
                    bstop.IsEnabled = true;
                    bstart.IsEnabled = false;
                    waitHandle.Set();
                };

                bstop.Click += (sender, args) =>
                {
                    if (_threads[nr].IsAlive)
                    {
                        waitHandle.Reset();
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
            _textBox.Dispatcher.BeginInvoke(new Action(() =>
            {
                _textBox.AppendText(message + "\n");
                _textBox.ScrollToEnd();
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
