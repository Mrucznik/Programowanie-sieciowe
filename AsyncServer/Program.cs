using System;

namespace AsyncServer
{
    class Program
    {
        static void Main()
        {
            Console.WriteLine("Provide communication port: ");
            if (!int.TryParse(Console.ReadLine(), out var port))
                port = 7;

            AsynchronousSocketListener serverEcho = new AsynchronousSocketListener();
            serverEcho.StartListening("127.0.0.1", port);
        }
    }
}
