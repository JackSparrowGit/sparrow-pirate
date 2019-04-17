/*
  thrift文件生成java文件指令: thrift -gen java Gamma.thrift
  如果遇到生成后的文件有很多@Override 报错，只需要把idea右下角设为Syntax
 */
namespace java com.corsair.sparrow.pirate.gamma.api

struct GammaDTO{
    1: i64 id,
    2: string name
}

struct GammaQuery{
    1: i64 id,
    2: string name
}

service GammaThriftService{

    GammaDTO getById(1:i64 id),
    list<GammaDTO> search(1: GammaQuery query)
}