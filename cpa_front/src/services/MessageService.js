import { toast } from "react-toastify";

const config = {
  position: "bottom-right",
  autoClose: 4000,
  hideProgressBar: false,
  closeOnClick: true,
  pauseOnHover: true,
  draggable: true,
  progress: undefined,
  theme: "dark"
};

class MessageService {
  
    successMessage(message, customConfig = {}) {
      return toast.success(message, {...config, ...customConfig});
    }
    
    infoMessage(message, customConfig = {}) {
      return toast.info(message, {...config, ...customConfig});
    }
    
    errorMessage = (message, customConfig = {}) => {
      return toast.error(message, {...config, ...customConfig});
    }

    updateToast = (toastId, customConfig = {}) => {
      return toast.update(toastId, {...config, ...customConfig})
    }

    updateProgress = (toastId, progress) => {
      toast.update(toastId.current, {
        progress: progress
      })
    }
}

const instance = new MessageService();

export default instance;