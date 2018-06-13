using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ZaliczenieKlient
{
    class Sender
    {
        private readonly IPEndPoint _endpoint;
        private readonly UdpClient _sender;

        public Sender(UdpClient sender, IPEndPoint endpoint)
        {
            _endpoint = endpoint;
            _sender = sender;
        }

        public void SendMessage(string message)
        {
            Thread sendThread = new Thread(() =>
            {
                var buffer = Encoding.Unicode.GetBytes(message);
                _sender.Send(buffer, buffer.Length, _endpoint);
            });
            sendThread.Start();
        }
    }
}
