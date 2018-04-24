using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace AsyncServer
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Provide communication port: ");
            if (!int.TryParse(Console.ReadLine(), out var port))
                port = 7;

            AsynchronousSocketListener serverEcho = new AsynchronousSocketListener();
            serverEcho.StartListening("127.0.0.1", port);
        }
    }
}
