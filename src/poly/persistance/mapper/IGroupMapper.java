package poly.persistance.mapper;

import config.Mapper;
import poly.dto.GroupDTO;

import java.util.List;

@Mapper("GroupMapper")
public interface IGroupMapper {

    int MakeGG(GroupDTO gDTO) throws Exception;

    int MkGG(GroupDTO gDTO) throws Exception;

    List<GroupDTO> getgg(GroupDTO gDTO) throws Exception;

    String GnCheck(String gname) throws Exception;

    int Delgu(GroupDTO gDTO) throws Exception;

    String already(GroupDTO gDTO) throws Exception;

    GroupDTO getGroupInfo(String seq) throws Exception;
}
