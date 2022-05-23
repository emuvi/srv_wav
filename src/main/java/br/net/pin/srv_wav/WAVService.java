package br.net.pin.srv_wav;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WAVService {
  @PostMapping("/merge")
  public @ResponseBody DidMerge merge(@RequestBody ForMerge forMerge) throws Exception {
    var response = new DidMerge();
    response.mergedWAV = WAVWorker.merge(forMerge.wavPaths);
    return response;
  }
}
