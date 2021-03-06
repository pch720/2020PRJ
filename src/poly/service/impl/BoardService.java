package poly.service.impl;

import org.springframework.stereotype.Service;
import poly.dto.BoardDTO;
import poly.dto.GroupDTO;
import poly.persistance.mapper.IBoardMapper;
import poly.service.IBoardService;

import javax.annotation.Resource;
import java.util.List;

@Service("BoardService")
public class BoardService implements IBoardService {

    @Resource(name = "BoardMapper")
    private IBoardMapper boardMapper;

    @Override
    public int write(BoardDTO bDTO) throws Exception {
        return boardMapper.write(bDTO);
    }

    @Override
    public List<BoardDTO> getnotice(String seq) throws Exception {
        return boardMapper.getnotice(seq);
    }

    @Override
    public int delwork(BoardDTO bDTO) throws Exception {
        return boardMapper.delwork(bDTO);
    }

    @Override
    public int finwork(BoardDTO bDTO) throws Exception {
        return boardMapper.finwork(bDTO);
    }

    @Override
    public List<BoardDTO> getUnotice(GroupDTO gDTO) throws Exception {
        return boardMapper.getUnotice(gDTO);
    }

    @Override
    public int MNotice(BoardDTO bDTO) throws Exception {
        return boardMapper.MNotice(bDTO);
    }

    @Override
    public int writerep(BoardDTO bDTO) throws Exception {
        return boardMapper.writerep(bDTO);
    }

    @Override
    public List<BoardDTO> getrep(String seq) throws Exception {
        return boardMapper.getrep(seq);
    }

    @Override
    public int delrep(String seq) throws Exception {
        return boardMapper.delrep(seq);
    }

    @Override
    public int like(BoardDTO bDTO) throws Exception {
        return boardMapper.like(bDTO);
    }

    @Override
    public int ML(BoardDTO bDTO) throws Exception {
        return boardMapper.ML(bDTO);
    }

    @Override
    public int DL(BoardDTO bDTO) throws Exception {
        return boardMapper.DL(bDTO);
    }

    @Override
    public String CL(BoardDTO bDTO) throws Exception {
        return boardMapper.CL(bDTO);
    }

    @Override
    public List<BoardDTO> getN() throws Exception {
        return boardMapper.getN();
    }

    @Override
    public int Notice(BoardDTO bDTO) throws Exception {
        return boardMapper.Notice(bDTO);
    }

    @Override
    public int MNotice1(BoardDTO bDTO) throws Exception {
        return boardMapper.MNotice1(bDTO);
    }

    @Override
    public int delwork1(BoardDTO bDTO) throws Exception {
        return boardMapper.delwork1(bDTO);
    }

    @Override
    public int writechat(BoardDTO bDTO) throws Exception {
        return boardMapper.writechat(bDTO);
    }

    @Override
    public List<BoardDTO> getChat(String seq) throws Exception {
        return boardMapper.getChat(seq);
    }
}
