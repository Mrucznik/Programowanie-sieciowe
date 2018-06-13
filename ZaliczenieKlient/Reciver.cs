using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace ZaliczenieKlient
{
    class Reciver
    {
        private IPEndPoint _endpoint;
        private UdpClient _reciver;

        public Reciver(UdpClient reciver, IPEndPoint endpoint)
        {
            _endpoint = endpoint;
            _reciver = reciver;
        }
        
        public string Recive()
        {
            Byte[] data = _reciver.Receive(ref _endpoint);
            string message = Encoding.Unicode.GetString(data);
            return message;
        }
    }
}
